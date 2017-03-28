package com.tristenallen.watersource.reports;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.tristenallen.watersource.R;

/**
 * Created by jahziel on 3/27/17.
 */
public class SelectYearVCActivity extends AppCompatActivity {
    Button submitGraphDetailsButton;
    Spinner yearSpinner;
    RadioButton virus;
    RadioButton contaminant;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectyearvc);
        submitGraphDetailsButton = (Button) findViewById(R.id.submitGraphDetailsButton);
        yearSpinner = (Spinner) findViewById(R.id.year);
        virus = (RadioButton) findViewById(R.id.virus);
        contaminant = (RadioButton) findViewById(R.id.contaminant);

        submitGraphDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: get data from the spinner and RBs and send it to the graph
                Intent goToGraph = new Intent(getApplicationContext(), HistographActivity.class);
                startActivity(goToGraph);
            }
        });
    }


}
