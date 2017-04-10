package com.tristenallen.watersource.model;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.support.test.runner.AndroidJUnit4;
import com.google.gson.Gson;
import com.tristenallen.watersource.database.MyDatabase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by jahziel on 4/10/17.
 * JUnits class to test the getPurityReports method.
 */
public class GetPurityReportsTest {
    private ReportHelper reportHelper;
    private Activity context = mock(Activity.class);
    private Gson gson = new Gson();
    //private PurityReport pR = new PurityReport(10, new Location("test"), WaterPurity.SAFE, 40, 42, 24);
    //private String goodRepStr = gson.toJson(pR);

    @Before
    public void setUp() {
        SharedPreferences sharedPreferences = mock(SharedPreferences.class);
        /*when(sharedPreferences.getString("10", null)).thenReturn(goodRepStr);
        when(sharedPreferences.contains("10")).thenReturn(true);
        when(context.getSharedPreferences("PurityReports", Context.MODE_PRIVATE)).thenReturn(sharedPreferences);*/
        reportHelper = Model.getReportHelper();
    }

    @Test
    public void testSize() {
        reportHelper.addPurityReport(10, new Location("test"), WaterPurity.SAFE, 40, 42, new MyDatabase(context), context);
        assertEquals("FAILURE! actual: " + reportHelper.getPurityReports(context).size(), 1, reportHelper.getPurityReports(context).size());
    }
}
