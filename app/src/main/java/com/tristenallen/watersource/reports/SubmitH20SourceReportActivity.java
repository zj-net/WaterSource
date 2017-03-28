package com.tristenallen.watersource.reports;

/**
 * Created by jahziel on 2/27/17.
 */

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
import com.tristenallen.watersource.main.MainActivity;
import android.content.Context;
import android.widget.ArrayAdapter;
import android.location.Location;
import com.tristenallen.watersource.model.Model;
import com.tristenallen.watersource.model.ReportHelper;
import com.tristenallen.watersource.model.WaterQuality;
import com.tristenallen.watersource.model.WaterType;

public class SubmitH20SourceReportActivity extends AppCompatActivity {
    private EditText latField;
    private EditText lngField;
    private Spinner waterTypeSpinner;
    private Spinner waterQualSpinner;
    private Button submitButton;
    private double latDouble;
    private double lngDouble;
    private Location h20Loc = new Location("Water Report Location");
    private ReportHelper reportHelper = Model.getReportHelper();
    private LatLng latLng;
    private boolean badLat;
    private boolean badLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sourcereport);
        //init the vars
        latField = (EditText) findViewById(R.id.latitudeTXT);
        lngField = (EditText) findViewById(R.id.longitudeTXT);
        waterTypeSpinner = (Spinner) findViewById(R.id.waterType);
        waterQualSpinner = (Spinner) findViewById(R.id.waterCondition);
        submitButton = (Button) findViewById(R.id.submitButton);

        //populate spinners
        waterTypeSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, WaterType.values()));
        waterQualSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, WaterQuality.values()));

        // if latLng of a newly added marker is passed in, set latLng to it.
        if (getIntent().hasExtra(MainActivity.ARG_latLng)) {
            latLng = getIntent().getParcelableExtra(MainActivity.ARG_latLng);
            latField.setText(Double.toString(latLng.latitude));
            lngField.setText(Double.toString(latLng.longitude));
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

                } else if (latDouble > 90.0 || latDouble < -90.0 || badLat) {
                    //throw a fit!
                    Context context = getApplicationContext();
                    CharSequence error = "Please enter a number between +/- 90!";
                    int duration = Toast.LENGTH_LONG;
                    latField.setText("");
                    Toast badLatitude = Toast.makeText(context, error, duration);
                    badLatitude.show();
                } else if (lngDouble > 180.0 || lngDouble < -180.0 || badLng) {
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
                    reportHelper.addSourceReport(Model.getCurrentUserID(), h20Loc, waterQualityData, waterTypeData);
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
