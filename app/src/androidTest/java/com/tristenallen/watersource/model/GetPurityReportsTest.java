package com.tristenallen.watersource.model;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.provider.ContactsContract;
import android.support.test.runner.AndroidJUnit4;
import android.test.InstrumentationTestCase;
import android.test.RenamingDelegatingContext;
import com.google.gson.Gson;
import com.tristenallen.watersource.database.MyDatabase;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import  android.support.test.InstrumentationRegistry;

import java.util.ArrayList;


import static org.junit.Assert.assertEquals;

/**
 * Created by jahziel on 4/10/17.
 * JUnits class to test the getPurityReports method.
 */
public class GetPurityReportsTest {
    private ReportHelper reportHelper = ReportHelper.getInstance();
    private UserHelper userHelper = UserHelper.getInstance();
    private Context context = InstrumentationRegistry.getTargetContext();
    private DataSource dataSource = new MyDatabase(context);
    private PurityReport real = new PurityReport(0, new Location("test1"), WaterPurity.SAFE, 1, 40, 42);
    private PurityReport real2 = new PurityReport(0, new Location("test2"), WaterPurity.SAFE, 1, 50, 52);
    private PurityReport[] realArr = {real, real2};
    private ArrayList<PurityReport> pL;

    @Before
    public void setUp() {
        User added = new User("d@d.com", AuthLevel.ADMIN, "sweaty", "bepis");
        userHelper.addUser(added, "d@d.com", "d", dataSource);
        reportHelper.addPurityReport(0, new Location("test1"), WaterPurity.SAFE, 40, 42, dataSource, context);
        reportHelper.addPurityReport(0, new Location("test2"), WaterPurity.SAFE, 50, 52, dataSource, context);
        pL = new ArrayList<>(reportHelper.getPurityReports(context));
    }

    @Test
    public void testSize() {
        assertEquals("FAILURE!", 2, reportHelper.getPurityReports(context).size());
    }

    @Test
    public void testCorrectUserID() {
        for (int i = 0; i < 2; i++) {
            assertEquals("FAILURE!", realArr[i].getUserID(), pL.get(i).getUserID());
        }

    }

    @Test
    public void testCorrectWaterPurity() {
        //ArrayList<PurityReport> pL = new ArrayList<>(reportHelper.getPurityReports(context));
        for (int i = 0; i < 2; i++) {
            assertEquals("FAILURE!", realArr[i].getPurity(), pL.get(i).getPurity());
        }
    }

}
