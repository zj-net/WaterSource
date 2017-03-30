package com.tristenallen.watersource.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.util.Log;

import com.tristenallen.watersource.model.SourceReport;
import com.tristenallen.watersource.model.WaterQuality;
import com.tristenallen.watersource.model.WaterType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by David on 3/29/17.
 */

public class SourceReportDB {

    // Database table column names
    public static final String TABLE_NAME = "SourceReport";

    public static final String COLUMN_REPORTID = "reportID";
    public static final String COLUMN_USERID = "userID";
    public static final String COLUMN_YEAR = "year";
    public static final String COLUMN_MONTH = "month";
    public static final String COLUMN_DAY = "day";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_QUALITY = "quality";

    public static final String[] allColumns = { SourceReportDB.COLUMN_REPORTID,
            SourceReportDB.COLUMN_USERID, SourceReportDB.COLUMN_YEAR, SourceReportDB.COLUMN_MONTH,
            SourceReportDB.COLUMN_DAY, SourceReportDB.COLUMN_LATITUDE,SourceReportDB.COLUMN_LONGITUDE,
            SourceReportDB.COLUMN_TYPE, SourceReportDB.COLUMN_QUALITY};

    private static final int REPORTID_NUMBER = 0;
    private static final int USERID_NUMBER = 1;
    private static final int YEAR_NUMBER = 2;
    private static final int MONTH_NUMBER = 3;
    private static final int DAY_NUMBER = 4;
    private static final int LONGITUDE_NUMBER = 5;
    private static final int LATITUDE_NUMBER = 6;
    private static final int TYPE_NUMBER = 7;
    private static final int QUALITY_NUMBER = 8;

    // Database creation SQL statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_NAME
            + "("
            + COLUMN_REPORTID + " integer primary key autoincrement, "
            + COLUMN_USERID + " integer,"
            + COLUMN_YEAR + " integer,"
            + COLUMN_MONTH + " integer,"
            + COLUMN_DAY + " integer,"
            + COLUMN_LATITUDE + " real,"
            + COLUMN_LONGITUDE + " real,"
            + COLUMN_TYPE + " text not null,"
            + COLUMN_QUALITY + " text not null"
            + ");";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion,
                                 int newVersion) {
        Log.w(UserDB.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(database);
    }

    public static SourceReport create(SQLiteDatabase database, int reportID, int userID,
                                      Date timestamp, Location location, WaterType type,
                                      WaterQuality quality) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_REPORTID, reportID);
        values.put(COLUMN_USERID, userID);
        values.put(COLUMN_YEAR, timestamp.getYear());
        values.put(COLUMN_MONTH, timestamp.getMonth());
        values.put(COLUMN_DAY, timestamp.getDay());
        values.put(COLUMN_LATITUDE, location.getLatitude());
        values.put(COLUMN_LONGITUDE, location.getLongitude());
        values.put(COLUMN_TYPE, type.toString());
        values.put(COLUMN_QUALITY, quality.toString());

        long insertId = database.insert(TABLE_NAME, null,
                values);

        Log.d("APP", "Inserted SourceReport: " + insertId);

        Cursor cursor = database.query(TABLE_NAME,
                allColumns, COLUMN_REPORTID + " = " + insertId, null,
                null, null, null);

        cursor.moveToFirst();

        SourceReport report = cursorToReport(cursor);
        cursor.close();
        return report;
    }

    private static SourceReport cursorToReport(Cursor cursor) {
        int reportID = cursor.getInt(REPORTID_NUMBER);
        int userID = cursor.getInt(USERID_NUMBER);

        double lat = cursor.getDouble(LATITUDE_NUMBER);
        double lng = cursor.getDouble(LONGITUDE_NUMBER);
        Location location = new Location("Water Source Location");
        location.setLatitude(lat);
        location.setLongitude(lng);

        WaterType type = WaterType.valueOf(cursor.getString(TYPE_NUMBER).toUpperCase());
        WaterQuality quality = WaterQuality.valueOf(cursor.getString(QUALITY_NUMBER).toUpperCase());

        SourceReport report = new SourceReport(userID,location,quality,type,reportID);
        return report;
    }


    public static List<SourceReport> getAllReports(SQLiteDatabase database) {
        List<SourceReport> reports = new ArrayList<>();
        Cursor cursor = database.query(TABLE_NAME,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            SourceReport report = cursorToReport(cursor);
            reports.add(report);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();

        Log.d("APP", "All Source Reports: " + reports.toString());

        return reports;
    }


    public static int getSourceReportCount(SQLiteDatabase database) {
        String query = "SELECT * FROM " + TABLE_NAME;

        Cursor  cursor = database.rawQuery(query,null);

        return cursor.getCount();
    }

}

