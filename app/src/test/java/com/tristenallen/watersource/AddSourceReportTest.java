package com.tristenallen.watersource;

import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import com.tristenallen.watersource.controller.SubmitPurityReportActivity;
import com.tristenallen.watersource.database.MyDatabase;
import com.tristenallen.watersource.model.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Allan on 4/5/17.
 */
public class AddSourceReportTest {
    ReportHelper reportHelper;
    MyDatabase data;
    User user = new User("allanserna@gmail.com", AuthLevel.ADMIN,"Serna","Allan");
    User nullUser = new User(null, AuthLevel.USER, null, null);

    @Before
    public void setUp() {
        data = mock(MyDatabase.class);
        when(data.checkEmail("false@nope.com")).thenReturn(false);
        when(data.checkEmail("allanserna@gmail.com")).thenReturn(true);
        when(data.getIDbyEmail("allan@gmail.com")).thenReturn(1);
        when(data.getIDbyEmail("false@nope.com")).thenReturn(0);
        when(data.validate("allanserna@gmail.com","1")).thenReturn(true);
        when(data.validate("allanserna@gmail.com","2")).thenReturn(false);
        when(data.getUserByID(1)).thenReturn(user);
        when(data.getUserByID(0)).thenReturn(nullUser);
        when(data.getIDbyEmail("false@nope.com")).thenReturn(0);

        reportHelper = ReportHelper.getInstance();
    }

    @Test
    // null user id
    public void InvalidUser() {
        Location loc = new Location("dummyprovider");
        loc.setLatitude(20.3);
        loc.setLongitude(52.6);
        reportHelper.addSourceReport(0, loc,
                WaterQuality.POTABLE, WaterType.STREAM, data, new SubmitPurityReportActivity());
    }

    @Test
    // valid user id
    public void ValidUser() {
        Location loc = new Location("dummyprovider");
        loc.setLatitude(20.3);
        loc.setLongitude(52.6);
        AppCompatActivity context = new SubmitPurityReportActivity();
        int ReportNumber = reportHelper.getSourceReports(context).size();
        reportHelper.addSourceReport(1, loc,
                WaterQuality.POTABLE , WaterType.STREAM, data, context);
        assertEquals(reportHelper.getSourceReports(context).size(), ReportNumber + 1);
    }

}