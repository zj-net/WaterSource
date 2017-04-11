package com.tristenallen.watersource.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.tristenallen.watersource.model.AuthLevel;
import com.tristenallen.watersource.model.DataSource;
import com.tristenallen.watersource.model.User;

/**
 * Created by David on 3/28/17.
 * The database implementation class.
 */

public class MyDatabase extends SQLiteOpenHelper implements DataSource {
    // Database table column names
    private static final String TABLE_NAME = "User";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_ROLE = "role";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_LASTNAME = "lastname";
    private static final String COLUMN_FIRSTNAME = "firstname";

    private static final String[] allColumns = { COLUMN_ID, COLUMN_PASSWORD,
            COLUMN_EMAIL, COLUMN_ROLE, COLUMN_ADDRESS, COLUMN_TITLE,
            COLUMN_LASTNAME, COLUMN_FIRSTNAME};

    private static final int ID_NUMBER = 0;
    private static final int EMAIL_NUMBER = 2;
    private static final int ROLE_NUMBER = 3;
    private static final int LASTNAME_NUMBER = 6;
    private static final int FIRSTNAME_NUMBER = 7;

    // Database creation SQL statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_NAME
            + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_PASSWORD + " text not null,"
            + COLUMN_EMAIL + " text not null,"
            + COLUMN_ROLE + " text not null,"
            + COLUMN_ADDRESS + " text not null,"
            + COLUMN_TITLE + " text not null,"
            + COLUMN_LASTNAME + " text not null,"
            + COLUMN_FIRSTNAME + " text not null"
            + ");";

    private static final String DATABASE_NAME = "student.db";
    private static final int DATABASE_VERSION = 1;
    private SQLiteDatabase database;

    /**
     * constructor method to create a database
     * @param context Context the activity context
     */
    public MyDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        open();
    }

    private void open() throws SQLException {
        database = getWritableDatabase();
    }

    /**
     * method for create the database
     */
    private void onCreate() {
        database.execSQL(DATABASE_CREATE);
    }

    /**
     * method update db from old version to new version
     * @param oldVersion int old version number
     * @param newVersion int new version number
     */
    private void onUpgrade(int oldVersion, int newVersion) {
        Log.w(MyDatabase.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate();
    }

    @SuppressWarnings("FeatureEnvy") // necessary as User is a data holder and we are converting to a DB format
    @Override
    public void createUser(int id, String password, String address, String title, User user) {
        ContentValues values = new ContentValues();
        AuthLevel role = user.getRole();
        values.put(COLUMN_ID, id);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_EMAIL, user.getEmail());
        values.put(COLUMN_ROLE, role.toString());
        values.put(COLUMN_ADDRESS, address);
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_LASTNAME, user.getLastName());
        values.put(COLUMN_FIRSTNAME, user.getFirstName());

        long insertId = database.insert(TABLE_NAME, null,
                values);

        Log.d("APP", "Inserted User: " + insertId);

        Cursor cursor = database.query(TABLE_NAME,
                allColumns, COLUMN_ID + " = " + insertId, null,
                null, null, null);

        cursor.moveToFirst();
        cursorToUser(cursor);
        cursor.close();
    }

    private static User cursorToUser(Cursor cursor) {
        String email = cursor.getString(EMAIL_NUMBER);
        String roleString = cursor.getString(ROLE_NUMBER);
        AuthLevel role = AuthLevel.valueOf(roleString.toUpperCase());
        String lastName = cursor.getString(LASTNAME_NUMBER);
        String firstName = cursor.getString(FIRSTNAME_NUMBER);
        return new User(email, role, lastName, firstName);
    }


    @Override
    public boolean checkEmail(String email) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_EMAIL + " ='" + email + "'";

        Cursor cursor = database.rawQuery(query,null);
        Log.d("checkEmail******",cursor.toString());

        int row_count = cursor.getCount();
        cursor.close();

        //return true for duplicate email
        //return false for legal email
        return row_count>0;
    }

    @Override
    public boolean validate(String email, String password) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_EMAIL + " ='"
                + email + "' AND " + COLUMN_PASSWORD + " ='" + password + "'";

        Cursor cursor = database.rawQuery(query,null);

        int row_count = cursor.getCount();
        cursor.close();

        //return true for email,password match
        //return false for wrong password
        return row_count>0;
    }

    @Override
    public int getIDbyEmail(String email) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_EMAIL + " ='" + email + "'";

        Cursor cursor = database.rawQuery(query,null);

        if (cursor.getCount()>0) {
            cursor.moveToFirst();
            int id = cursor.getInt(ID_NUMBER);
            cursor.close();
            return id;
        }
        else {
            cursor.close();
            // does not exist
            return -1;
        }
    }

    @Override
    public User getUserByID(int id) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = " + id;

        Cursor  cursor = database.rawQuery(query,null);

        User user = null;

        if (cursor.getCount()>0) {
            cursor.moveToFirst();

            user = cursorToUser(cursor);

            cursor.close();
        }

        return user;
    }

    @Override
    public int getUserCount() {
        String query = "SELECT * FROM " + TABLE_NAME;

        Cursor  cursor = database.rawQuery(query,null);

        int row_count = cursor.getCount();
        cursor.close();

        return row_count;
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        onCreate();
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion,
                          int newVersion) {
        onUpgrade(oldVersion, newVersion);
    }

}

