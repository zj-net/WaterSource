package com.tristenallen.watersource.reports;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.tristenallen.watersource.R;
import android.widget.Button;
import com.tristenallen.watersource.main.MainActivity;
import android.content.Context;
import android.widget.ArrayAdapter;
import android.location.Location;
import com.tristenallen.watersource.model.Model;
import com.tristenallen.watersource.model.ReportHelper;
import com.tristenallen.watersource.model.WaterQuality;
import com.tristenallen.watersource.model.WaterType;

//imports from that one website
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

/**
 * Created by jahziel on 3/27/17.
 */
public class HistographActivity extends AppCompatActivity {
    private String[] splitStr;
    private DataPoint[] dataPointsArray = {new DataPoint(0, 0)};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_histograph);
        final ArrayList<String> extrasFromIntent = getIntent().getStringArrayListExtra("YearAndVC");

        GraphView graph = (GraphView) findViewById(R.id.graph);
        ArrayList<DataPoint> dataPoints = new ArrayList<>();
        for (String s : extrasFromIntent) {
            splitStr = s.split(":");
            dataPoints.add(new DataPoint(Double.parseDouble(splitStr[0]), Double.parseDouble(splitStr[1])));
        }
        dataPoints.toArray(dataPointsArray);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPointsArray);
        graph.addSeries(series);
    }
}
