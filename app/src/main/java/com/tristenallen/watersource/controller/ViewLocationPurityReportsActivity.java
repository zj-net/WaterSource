package com.tristenallen.watersource.controller;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import com.tristenallen.watersource.R;
import com.tristenallen.watersource.model.Model;
import com.tristenallen.watersource.model.PurityReport;
import com.tristenallen.watersource.model.ReportHelper;
import com.tristenallen.watersource.reports.HistographActivity;
import com.tristenallen.watersource.reports.SelectYearVCActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by jahziel on 3/28/17.
 */
public class ViewLocationPurityReportsActivity extends AppCompatActivity {
    private ReportHelper reportHelper = Model.getReportHelper();
    private ListView listView;
    private Button viewHistographButton;
    private Button addNewPurityReportButton;
    private double[] extrasFromInfoWindow;
    private double[] location = new double[2];
    private ArrayList<String> monthYearVC = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewlocationpurityreports);
        listView = (ListView) findViewById(R.id.locationPurityReportListView);
        viewHistographButton = (Button) findViewById(R.id.viewHistographButton);
        addNewPurityReportButton = (Button) findViewById(R.id.addNewReportButton);
        extrasFromInfoWindow = getIntent().getDoubleArrayExtra("Location");
        List<PurityReport> rawPurityReports = new ArrayList<>(reportHelper.getPurityReports(this));
        List<PurityReport> purityReports = new ArrayList<>();

        List<String> purityReportStrings = new ArrayList<>();

        for (PurityReport x : rawPurityReports) {
            if (x.getLocation().getLatitude() == extrasFromInfoWindow[0] && x.getLocation().getLongitude() == extrasFromInfoWindow[1]) {
                purityReports.add(x);
            }
        }

        PurityReport sample = purityReports.get(0);
        location[0] = sample.getLocation().getLatitude();
        location[1] = sample.getLocation().getLongitude();

        for (PurityReport p : purityReports) {
            purityReportStrings.add(p.toString());
            Date leDate =  p.getTimestamp();
            Calendar cal = Calendar.getInstance();
            cal.setTime(leDate);
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int virusPPM = p.getVirusPPM();
            int contPPM = p.getContaminantPPM();
            monthYearVC.add(month + ":" + year + ":" + virusPPM + ":" + contPPM);
        }
        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, purityReportStrings));

        viewHistographButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToSelect = new Intent(getApplicationContext(), SelectYearVCActivity.class);
                goToSelect.putExtra("monthYearVC", monthYearVC);
                startActivity(goToSelect);
            }
        });

        addNewPurityReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addAnother = new Intent(getApplicationContext(), SubmitPurityReportActivity.class);
                addAnother.putExtra("location", location);
                startActivity(addAnother);
            }
        });


    }
}
