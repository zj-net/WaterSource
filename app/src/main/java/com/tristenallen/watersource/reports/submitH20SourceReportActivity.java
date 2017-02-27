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
import com.tristenallen.watersource.model.WaterType;
import com.tristenallen.watersource.model.WaterQuality;
import android.widget.ArrayAdapter;

public class submitH20SourceReportActivity extends AppCompatActivity {
    private EditText latField;
    private EditText lngField;
    private Spinner waterTypeSpinner;
    private Spinner waterCondSpinner;
    private Button submitButton;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sourcereport);
        //init the vars
        latField = (EditText) findViewById(R.id.latitudeTXT);
        lngField = (EditText) findViewById(R.id.longitudeTXT);
        waterTypeSpinner = (Spinner) findViewById(R.id.waterType);
        waterCondSpinner = (Spinner) findViewById(R.id.waterCondition);
        submitButton = (Button) findViewById(R.id.submitButton);
        backButton = (Button) findViewById(R.id.backButton);

        //populate spinners
        waterTypeSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, WaterType.values()));
        waterCondSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, WaterQuality.values()));

        //back button handling

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



    }

}
