package com.tristenallen.watersource.reports;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.tristenallen.watersource.R;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by jahziel on 3/27/17.
 */
public class SelectYearVCActivity extends AppCompatActivity {
    private Spinner yearSpinner;
    private RadioButton virus;
    private RadioButton contaminant;
    private boolean virusTrue = false;
    private boolean contTrue = false;
    private String[] splitStr;
    private final ArrayList<String> extrasForGraph = new ArrayList<>();
    private final ArrayList<Integer> YEARS = new ArrayList<>();
    private static final int FIRST_YEAR = 1970;
    private Iterable<String> monthYearVC = new ArrayList<>();

    private boolean checkVirus = false;
    private boolean checkContaminant = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_selectyearvc);
        Button submitGraphDetailsButton = (Button) findViewById(R.id.submitGraphDetailsButton);
        yearSpinner = (Spinner) findViewById(R.id.year);
        virus = (RadioButton) findViewById(R.id.virus);
        contaminant = (RadioButton) findViewById(R.id.contaminant);
        int THIS_YEAR = Calendar.getInstance().get(Calendar.YEAR);

        monthYearVC = getIntent().getStringArrayListExtra("monthYearVC");
        for (int i = THIS_YEAR; i >= FIRST_YEAR; i--) {
            YEARS.add(i);
        }


        virus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (virus.isChecked()) {
                    if (!checkVirus) {
                        virus.setChecked(true);
                        checkVirus = true;
                    } else {
                        checkVirus = false;
                        virus.setChecked(false);
                    }
                }
            }
        });

        contaminant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (contaminant.isChecked()) {
                    if (!checkContaminant) {
                        contaminant.setChecked(true);
                        checkContaminant = true;
                    } else {
                        checkContaminant = false;
                        contaminant.setChecked(false);
                    }
                }
            }
        });



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
                if (virusTrue) {
                    goToGraph.putExtra("v", "v");
                } else if (contTrue) {
                    goToGraph.putExtra("c", "c");
                }
                startActivity(goToGraph);
            }
        });
    }


}
