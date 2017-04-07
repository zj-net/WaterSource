package com.tristenallen.watersource.reports;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.Series;
import com.tristenallen.watersource.R;


//imports from that one website
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

/**
 * Created by jahziel on 3/27/17.
 */
public class HistographActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_histograph);
        final Iterable<String> extrasFromIntent = getIntent().getStringArrayListExtra("YearAndVC");

        GraphView graph = (GraphView) findViewById(R.id.graph);
        ArrayList<DataPoint> dataPoints = new ArrayList<>();
        for (String s : extrasFromIntent) {
            String[] splitStr = s.split(":");
            dataPoints.add(new DataPoint(Double.parseDouble(splitStr[0]), Double.parseDouble(splitStr[1])));
        }
        DataPoint[] dataPointsArray = new DataPoint[dataPoints.size()];
        dataPointsArray = dataPoints.toArray(dataPointsArray);
        Series<DataPoint> series = new LineGraphSeries<>(dataPointsArray);
        graph.addSeries(series);
        GridLabelRenderer gridLabelRenderer = graph.getGridLabelRenderer();
        gridLabelRenderer.setHorizontalAxisTitle("TIME (MONTHS)");
        if (getIntent().hasExtra("v")) {
            gridLabelRenderer.setVerticalAxisTitle("VIRUS LEVELS (PPM)");
        } else if (getIntent().hasExtra("c")) {
            gridLabelRenderer.setVerticalAxisTitle("CONTAMINANT LEVELS (PPM)");
        }
    }
}
