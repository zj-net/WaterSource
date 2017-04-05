package com.tristenallen.watersource.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tristenallen.watersource.model.AuthLevel;
import com.tristenallen.watersource.model.User;

/**
 * Created by David on 3/28/17.
 */

public class UserDB {
    // Database table column names
    public static final String TABLE_NAME = "User";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_ROLE = "role";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_LASTNAME = "lastname";
    public static final String COLUMN_FIRSTNAME = "firstname";

    public static final String[] allColumns = { UserDB.COLUMN_ID, UserDB.COLUMN_PASSWORD,
            UserDB.COLUMN_EMAIL, UserDB.COLUMN_ROLE, UserDB.COLUMN_ADDRESS, UserDB.COLUMN_TITLE,
            UserDB.COLUMN_LASTNAME, UserDB.COLUMN_FIRSTNAME};

    private static final int ID_NUMBER = 0;
    private static final int PASSWORD_NUMBER = 1;
    private static final int EMAIL_NUMBER = 2;
    private static final int ROLE_NUMBER = 3;
    private static final int ADDRESS_NUMBER = 4;
    private static final int TITLE_NUMBER = 5;
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

    public static User create(SQLiteDatabase database, int id, String password, String email,
                              AuthLevel role, String address, String title, String lastName,
                              String firstName) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, id);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_ROLE, role.toString());
        values.put(COLUMN_ADDRESS, address);
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_LASTNAME, lastName);
        values.put(COLUMN_FIRSTNAME, firstName);

        long insertId = database.insert(TABLE_NAME, null,
                values);

        Log.d("APP", "Inserted User: " + insertId);

        Cursor cursor = database.query(TABLE_NAME,
                allColumns, COLUMN_ID + " = " + insertId, null,
                null, null, null);

        cursor.moveToFirst();

        User user = cursorToUser(cursor);
        cursor.close();
        return user;
    }

    private static User cursorToUser(Cursor cursor) {
        String email = cursor.getString(EMAIL_NUMBER);
        AuthLevel role = AuthLevel.valueOf(cursor.getString(ROLE_NUMBER).toUpperCase());
        String lastName = cursor.getString(LASTNAME_NUMBER);
        String firstName = cursor.getString(FIRSTNAME_NUMBER);
        User user = new User(email, role, lastName, firstName);
        return user;
    }


    public static List<User> getAllUsers(SQLiteDatabase database) {
        List<User> users = new ArrayList<>();
        Cursor cursor = database.query(TABLE_NAME,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            User user = cursorToUser(cursor);
            users.add(user);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();

        Log.d("APP", "All Users: " + users.toString());

        return users;
    }

    //delete user by id
    public static void delete(SQLiteDatabase database, int id) {
        database.delete(TABLE_NAME, COLUMN_ID + " = " + id, null);
    }

    public static boolean checkEmail(SQLiteDatabase database, String email) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_EMAIL + " ='" + email + "'";

        Cursor cursor = database.rawQuery(query,null);
        Log.d("checkEmail******",cursor.toString());
        if (cursor.getCount()>0) {
            // duplicate email
            return true;
        }
        else {
            // legal email
            return false;
        }
    }

    public static boolean validate(SQLiteDatabase database, String email, String password) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_EMAIL + " ='"
                + email + "' AND " + COLUMN_PASSWORD + " ='" + password + "'";

        Cursor cursor = database.rawQuery(query,null);

        if (cursor.getCount()>0) {
            // email,password match
            return true;
        }
        else {
            // wrong password
            return false;
        }
    }

    public static int getIDbyEmail(SQLiteDatabase database, String email) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_EMAIL + " ='" + email + "'";

        Cursor cursor = database.rawQuery(query,null);

        if (cursor.getCount()>0) {
            cursor.moveToFirst();
            return cursor.getInt(ID_NUMBER);
        }
        else {
            // does not exist
            return -1;
        }
    }

    public static User getUserByID(SQLiteDatabase database, int id) {
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

    public static int getUserCount(SQLiteDatabase database) {
        String query = "SELECT * FROM " + TABLE_NAME;

        Cursor  cursor = database.rawQuery(query,null);

        return cursor.getCount();
    }

}

