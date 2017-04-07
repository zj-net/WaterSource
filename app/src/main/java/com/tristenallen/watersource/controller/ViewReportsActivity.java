package com.tristenallen.watersource.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.tristenallen.watersource.R;
import com.tristenallen.watersource.model.Model;
import com.tristenallen.watersource.model.ReportHelper;
import com.tristenallen.watersource.model.SourceReport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jahziel on 3/1/17.
 * Activity for viewing source reports.
 */
public class ViewReportsActivity extends AppCompatActivity {
    private final ReportHelper reportHelper = Model.getReportHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewreports);
        ListView listView = (ListView) findViewById(R.id.sourceReportListView);
        Iterable<SourceReport> sourceReports = new ArrayList<>(reportHelper.getSourceReports(this));
        List<String> sourceReportStrings = new ArrayList<>();
        for (SourceReport s : sourceReports) {
            sourceReportStrings.add(s.toString());
        }
        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, sourceReportStrings));


    }
}
