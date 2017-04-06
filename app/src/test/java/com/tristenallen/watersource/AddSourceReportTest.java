package com.tristenallen.watersource;
import com.tristenallen.watersource.database.MyDatabase;

import com.tristenallen.watersource.model.AuthLevel;
import com.tristenallen.watersource.model.Model;
import com.tristenallen.watersource.model.SourceReport;
import com.tristenallen.watersource.model.WaterQuality;
import com.tristenallen.watersource.model.WaterType;
import com.tristenallen.watersource.model.DataSource;
import com.tristenallen.watersource.model.ReportHelper;
import com.tristenallen.watersource.model.User;
import com.tristenallen.watersource.controller.*;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

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
        when(data.getUserbyID(1)).thenReturn(user);
        when(data.getUserbyID(0)).thenReturn(nullUser);
        when(data.getIDbyEmail("false@nope.com")).thenReturn(0);

        reportHelper = Model.getReportHelper();
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