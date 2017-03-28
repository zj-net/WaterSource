package com.tristenallen.watersource.controller;

import android.content.Intent;
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
 * Created by jahziel on 3/27/17.
 */
public class ViewPurityReportsActivity extends AppCompatActivity {
    private ReportHelper reportHelper = Model.getReportHelper();
    private ListView listView;
    private Button viewGraphButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpurityreports);
        listView = (ListView) findViewById(R.id.purityReportListView);
        viewGraphButton = (Button) findViewById(R.id.viewGraphButton);
        List<PurityReport> purityReports = new ArrayList<>(reportHelper.getPurityReports());
        List<String> purityReportStrings = new ArrayList<>();
        for (PurityReport p : purityReports) {
            purityReportStrings.add(p.toString());
        }
        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, purityReportStrings));
        viewGraphButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToHistograph = new Intent(getApplicationContext(), HistographActivity.class);
                startActivity(goToHistograph);
            }
        });


    }
}
