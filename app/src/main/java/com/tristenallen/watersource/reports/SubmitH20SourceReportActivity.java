package com.tristenallen.watersource.reports;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.tristenallen.watersource.R;
import android.widget.Button;

import com.tristenallen.watersource.database.MyDatabase;
import com.tristenallen.watersource.main.MainActivity;
import android.content.Context;
import android.widget.ArrayAdapter;
import android.location.Location;

import com.tristenallen.watersource.model.DataSource;
import com.tristenallen.watersource.model.Model;
import com.tristenallen.watersource.model.ReportHelper;
import com.tristenallen.watersource.model.WaterQuality;
import com.tristenallen.watersource.model.WaterType;

import java.util.Locale;

/**
 * Created by jahziel on 2/27/17.
 * Activity for user to submit a new water source report.
 */
public class SubmitH20SourceReportActivity extends AppCompatActivity {
    private EditText latField;
    private EditText lngField;
    private Spinner waterTypeSpinner;
    private Spinner waterQualSpinner;
    private double latDouble;
    private double lngDouble;
    private final Location h20Loc = new Location("Water Report Location");
    private final ReportHelper reportHelper = Model.getReportHelper();
    private boolean badLat;
    private boolean badLng;

    private static final double LAT_MAX = 90;
    private static final double LAT_MIN = -90;
    private static final double LONG_MAX = 180;
    private static final double LONG_MIN = -180;

    private DataSource data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        data = new MyDatabase(this);

        setContentView(R.layout.activity_sourcereport);
        //init the vars
        latField = (EditText) findViewById(R.id.latitudeTXT);
        lngField = (EditText) findViewById(R.id.longitudeTXT);
        waterTypeSpinner = (Spinner) findViewById(R.id.waterType);
        waterQualSpinner = (Spinner) findViewById(R.id.waterCondition);
        Button submitButton = (Button) findViewById(R.id.submitButton);

        //populate spinners
        waterTypeSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                WaterType.values()));
        waterQualSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                WaterQuality.values()));

        // if latLng of a newly added marker is passed in, set latLng to it.
        if (getIntent().hasExtra(MainActivity.ARG_latLng)) {
            LatLng latLng = getIntent().getParcelableExtra(MainActivity.ARG_latLng);
            latField.setText(String.format(Locale.US, "%f", latLng.latitude));
            lngField.setText(String.format(Locale.US, "%f", latLng.longitude));
        }


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //------------------get all the data out------------------------
                WaterType waterTypeData = (WaterType) waterTypeSpinner.getSelectedItem();
                WaterQuality waterQualityData = (WaterQuality) waterQualSpinner.getSelectedItem();
                try {
                    badLat = false;
                    latDouble = Double.parseDouble(latField.getText().toString());
                }
                catch (NumberFormatException n) {
                    badLat = true;
                }
                try {
                    badLng = false;
                    lngDouble = Double.parseDouble(lngField.getText().toString());
                }
                catch (NumberFormatException n) {
                    badLng = true;
                }
                //--------------------------------------------------------------

                //--------------additional checks on lat and long---------------
                if (badLng && badLat) {
                    Context context = getApplicationContext();
                    CharSequence error = "Please enter valid latitude/longitude values!";
                    int duration = Toast.LENGTH_LONG;
                    lngField.setText("");
                    latField.setText("");
                    Toast badLng = Toast.makeText(context, error, duration);
                    badLng.show();

                } else if ((latDouble > LAT_MAX) || (latDouble < LAT_MIN) || badLat) {
                    //throw a fit!
                    Context context = getApplicationContext();
                    CharSequence error = "Please enter a number between +/- 90!";
                    int duration = Toast.LENGTH_LONG;
                    latField.setText("");
                    Toast badLatitude = Toast.makeText(context, error, duration);
                    badLatitude.show();
                } else if ((lngDouble > LONG_MAX) || (lngDouble < LONG_MIN) || badLng) {
                    //throw a fit!
                    Context context = getApplicationContext();
                    CharSequence error = "Please enter a number between +/- 180!";
                    int duration = Toast.LENGTH_LONG;
                    lngField.setText("");
                    Toast badLng = Toast.makeText(context, error, duration);
                    badLng.show();
                } else {
                    h20Loc.setLatitude(latDouble);
                    h20Loc.setLongitude(lngDouble);
                    reportHelper.addSourceReport(Model.getCurrentUserID(), h20Loc, waterQualityData, waterTypeData, data, SubmitH20SourceReportActivity.this);
                    Context context = getApplicationContext();
                    CharSequence msg = "Report submitted successfully!";
                    int duration = Toast.LENGTH_LONG;
                    Toast completedMsg = Toast.makeText(context, msg, duration);
                    completedMsg.show();
                    Intent goToMainScreen = new Intent(getApplicationContext(), MainActivity.class);
                    goToMainScreen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(goToMainScreen);
                }
                //--------------------------------------------------------------
            }
        });
    }

}
