package com.tristenallen.watersource.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by David on 3/28/17.
 * Helper class for the database.
 */

class MyDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "student.db";
    private static final int DATABASE_VERSION = 1;

    /**
     * Public constructor for MyDBHelper class.
     * @param context
     */
    public MyDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * wrapper method for create the database
     * @param database SQLiteDatabase the database used
     */
    @Override
    public void onCreate(SQLiteDatabase database) {
        UserDB.onCreate(database);
    }

    /**
     * wrapper method update db from old version to new version
     * @param database SQLiteDatabase the database used
     * @param oldVersion int old version number
     * @param newVersion int new version number
     */
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion,
                          int newVersion) {
        UserDB.onUpgrade(database, oldVersion, newVersion);
    }
}