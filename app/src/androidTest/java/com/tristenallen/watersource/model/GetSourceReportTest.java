package com.tristenallen.watersource.model;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.support.test.runner.AndroidJUnit4;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Tristen Allen on 4/5/17.
 *
 * This tests the getSourceReport method in ReportHelper, including passing improper ID numbers
 * and IDs not in the mocked database.
 */

@RunWith(AndroidJUnit4.class)
public class GetSourceReportTest {
    private ReportHelper reportHelper;
    private Activity context = mock(Activity.class);
    private Gson gson = new Gson();
    private SourceReport validReport = new SourceReport(1, new Location("test"), WaterQuality.CLEAR,
            WaterType.LAKE, 1);
    private String validReportString = gson.toJson(validReport);

    @Before
    public void setUp() {
        SharedPreferences sharedPrefs = mock(SharedPreferences.class);
        when(sharedPrefs.getString("1", null)).thenReturn(validReportString);
        when(sharedPrefs.contains("1")).thenReturn(true);
        when(context.getSharedPreferences("QualityReports", Context.MODE_PRIVATE)).thenReturn(sharedPrefs);
        reportHelper = ReportHelper.getInstance();
    }

    @Test
    // invalid ID
    public void getInvalidID() {
        assertNull(reportHelper.getSourceReport(-1, context));
    }

    @Test
    // non-present ID
    public void getNonpresentID() {
        assertNull(reportHelper.getSourceReport(5, context));
    }

    @Test
    // valid ID
    public void getReportByID() {
        SourceReport retrieved = reportHelper.getSourceReport(1, context);
        assertEquals(validReport, retrieved);
    }

}