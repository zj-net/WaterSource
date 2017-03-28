package com.tristenallen.watersource.controller;

/**
 * Created by jahziel on 3/1/17.
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import com.tristenallen.watersource.login.LoginActivity;
import com.tristenallen.watersource.login.RegistrationActivity;
import com.tristenallen.watersource.R;
import com.tristenallen.watersource.model.Model;
import com.tristenallen.watersource.model.ReportHelper;
import com.tristenallen.watersource.model.SourceReport;

import java.util.ArrayList;
import java.util.List;

public class ViewReportsActivity extends AppCompatActivity {
    private ReportHelper reportHelper = Model.getReportHelper();
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewreports);
        listView = (ListView) findViewById(R.id.sourceReportListView);
        List<SourceReport> sourceReports = new ArrayList<>(reportHelper.getSourceReports());
        List<String> sourceReportStrings = new ArrayList<>();
        for (SourceReport s : sourceReports) {
            sourceReportStrings.add(s.toString());
        }
        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, sourceReportStrings));


    }
}
