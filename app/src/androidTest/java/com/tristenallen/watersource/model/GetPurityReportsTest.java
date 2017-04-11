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
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import  android.support.test.InstrumentationRegistry;
import static org.junit.Assert.assertEquals;

/**
 * Created by jahziel on 4/10/17.
 * JUnits class to test the getPurityReports method.
 */
public class GetPurityReportsTest {
    private ReportHelper reportHelper = ReportHelper.getInstance();
    private UserHelper userHelper = UserHelper.getInstance();
    private Context context = InstrumentationRegistry.getTargetContext();
    private Gson gson = new Gson();
    private PurityReport pR = new PurityReport(1, new Location("test"), WaterPurity.SAFE, 40, 42, 24);
    private String goodRepStr = gson.toJson(pR);

    @Before
    public void setUp() {
        User added = new User("d@d.com", AuthLevel.ADMIN, "sweaty", "bepis");
        userHelper.addUser(added, "d@d.com", "d", new MyDatabase(context));
    }

    @Test
    public void testSize() {
        reportHelper.addPurityReport(0, new Location("test"), WaterPurity.SAFE, 40, 42, new MyDatabase(context), context);
        assertEquals("FAILURE! actual: " + reportHelper.getPurityReports(context).size(), 1, reportHelper.getPurityReports(context).size());
    }
}
