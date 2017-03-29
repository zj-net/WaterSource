package com.tristenallen.watersource.reports;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.tristenallen.watersource.R;

import java.util.ArrayList;

/**
 * Created by jahziel on 3/27/17.
 */
public class SelectYearVCActivity extends AppCompatActivity {
    private Button submitGraphDetailsButton;
    private Spinner yearSpinner;
    private RadioButton virus;
    private RadioButton contaminant;
    private boolean virusTrue = false;
    private boolean contTrue = false;
    private String[] splitStr;
    private ArrayList<String> extrasForGraph = new ArrayList<>();
    private final ArrayList<Integer> YEARS = new ArrayList<>();
    private int FIRST_YEAR = 1917;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TODO: populate spinner!
        setContentView(R.layout.activity_selectyearvc);
        submitGraphDetailsButton = (Button) findViewById(R.id.submitGraphDetailsButton);
        yearSpinner = (Spinner) findViewById(R.id.year);
        virus = (RadioButton) findViewById(R.id.virus);
        contaminant = (RadioButton) findViewById(R.id.contaminant);

        final ArrayList<String> monthYearVC = getIntent().getStringArrayListExtra("monthYearVC");
        for (int i = 0; i <= 100; i++) {
            YEARS.add(FIRST_YEAR);
            FIRST_YEAR++;
        }

        yearSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, YEARS));

        submitGraphDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String chosenYear = yearSpinner.getSelectedItem().toString();

                if (virus.isChecked()) {
                    virusTrue = true;
                } else if (contaminant.isChecked()) {
                    contTrue = true;
                } //TODO: throw a fit if there's nothing selected

                for(String s : monthYearVC) {
                    splitStr = s.split(":");
                    if (splitStr[1].equals(chosenYear)) {
                        if (virusTrue) {
                            extrasForGraph.add(splitStr[0] + ":" + splitStr[2]);
                        } else if (contTrue) {
                            extrasForGraph.add(splitStr[0] + ":" + splitStr[3]);
                        }
                    }
                }

                /*if (extrasForGraph == null) {
                    throw new NullPointerException();
                }*/

                Intent goToGraph = new Intent(getApplicationContext(), HistographActivity.class);
                goToGraph.putExtra("YearAndVC", extrasForGraph);
                startActivity(goToGraph);
            }
        });
    }


}
