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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jahziel on 3/28/17.
 */
public class ViewLocationPurityReportsActivity extends AppCompatActivity {
    private ReportHelper reportHelper = Model.getReportHelper();
    private ListView listView;
    private Button viewHistographButton;
    private double[] extrasFromInfoWindow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewlocationpurityreports);
        listView = (ListView) findViewById(R.id.locationPurityReportListView);
        viewHistographButton = (Button) findViewById(R.id.viewHistographButton);
        extrasFromInfoWindow = getIntent().getDoubleArrayExtra("Location");
        List<PurityReport> rawPurityReports = new ArrayList<>(reportHelper.getPurityReports());
        List<PurityReport> purityReports = new ArrayList<>();
        List<String> purityReportStrings = new ArrayList<>();
        for (PurityReport x : rawPurityReports) {
            if (x.getLocation().getLatitude() == extrasFromInfoWindow[0] && x.getLocation().getLongitude() == extrasFromInfoWindow[1]) {
                purityReports.add(x);
            }
        }

        for (PurityReport p : purityReports) {
            purityReportStrings.add(p.toString());
        }
        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, purityReportStrings));

        viewHistographButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToGraph = new Intent(getApplicationContext(), HistographActivity.class);
                startActivity(goToGraph);
            }
        });


    }
}
