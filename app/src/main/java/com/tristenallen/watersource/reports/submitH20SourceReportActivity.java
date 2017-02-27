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
import com.tristenallen.watersource.R;
import android.widget.Button;
import com.tristenallen.watersource.main.MainActivity;
import android.content.Context;
import com.tristenallen.watersource.model.*;
import android.widget.ArrayAdapter;
import android.location.Location;

public class submitH20SourceReportActivity extends AppCompatActivity {
    private EditText latField;
    private EditText lngField;
    private Spinner waterTypeSpinner;
    private Spinner waterQualSpinner;
    private Button submitButton;
    private Button backButton;
    private double latDouble;
    private double lngDouble;
    private Location h20Loc = new Location("Water Report Location");
    private UserHelper userHelper = Model.getUserHelper();
    private ReportHelper reportHelper = Model.getReportHelper();

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
        backButton = (Button) findViewById(R.id.backButton);

        //populate spinners
        waterTypeSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, WaterType.values()));
        waterQualSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, WaterQuality.values()));

        //back button handling
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //------------------get all the data out------------------------
                WaterType waterTypeData = (WaterType) waterTypeSpinner.getSelectedItem();
                WaterQuality waterQualityData = (WaterQuality) waterQualSpinner.getSelectedItem();
                try {
                    latDouble = Double.parseDouble(latField.getText().toString());
                }
                catch (NumberFormatException n) {
                    Context context = getApplicationContext();
                    CharSequence error = "Please enter a number!";
                    int duration = Toast.LENGTH_LONG;
                    latField.setText("");
                    Toast badLatitude = Toast.makeText(context, error, duration);
                    badLatitude.show();
                }
                try {
                    lngDouble = Double.parseDouble(lngField.getText().toString());
                }
                catch (NumberFormatException n) {
                    Context context = getApplicationContext();
                    CharSequence error = "Please enter a number";
                    int duration = Toast.LENGTH_LONG;
                    lngField.setText("");
                    Toast badLng = Toast.makeText(context, error, duration);
                    badLng.show();
                }
                //--------------------------------------------------------------

                //--------------additional checks on lat and long---------------
                if (latDouble > 180.0 || latDouble < -180.0) {
                    //throw a fit!
                    Context context = getApplicationContext();
                    CharSequence error = "Please enter a number!";
                    int duration = Toast.LENGTH_LONG;
                    latField.setText("");
                    Toast badLatitude = Toast.makeText(context, error, duration);
                    badLatitude.show();
                } else if (lngDouble > 90.0 || lngDouble < -90.0) {
                    //throw a fit!
                    Context context = getApplicationContext();
                    CharSequence error = "Please enter a number";
                    int duration = Toast.LENGTH_LONG;
                    lngField.setText("");
                    Toast badLng = Toast.makeText(context, error, duration);
                    badLng.show();
                } else {
                    h20Loc.setLatitude(latDouble);
                    h20Loc.setLongitude(lngDouble);
                    reportHelper.addSourceReport(Model.getCurrentUserID(), h20Loc, waterQualityData, waterTypeData);
                    Context context = getApplicationContext();
                    CharSequence msg = "Report submitted successfullly!";
                    int duration = Toast.LENGTH_LONG;
                    Toast completedMsg = Toast.makeText(context, msg, duration);
                    completedMsg.show();
                    Intent goToMainScreen = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(goToMainScreen);
                }
                //--------------------------------------------------------------
            }
        });



    }

}
