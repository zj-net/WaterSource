package com.tristenallen.watersource.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.tristenallen.watersource.model.AuthLevel;
import com.tristenallen.watersource.model.DataSource;
import com.tristenallen.watersource.model.User;

/**
 * Created by David on 3/28/17.
 * The database implementation class.
 */

public class MyDatabase implements DataSource {


    private SQLiteDatabase database;
    private final MyDBHelper dbHelper;


    public MyDatabase(Context context) {
        dbHelper = new MyDBHelper(context);
        open();
    }

    private void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    @Override
    public void createUser(int id, String password, String email,
                           AuthLevel role, String address, String title, String lastName,
                           String firstName) {
        UserDB.create(database, id, password, email, role, address, title, lastName,
                firstName);
    }

    @Override
    public boolean checkEmail(String email) {
        return UserDB.checkEmail(database, email);
    }

    @Override
    public boolean validate(String email, String password) {
        return UserDB.validate(database, email, password);
    }

    @Override
    public  int getIDbyEmail(String email) {
        return UserDB.getIDbyEmail(database, email);
    }

    @Override
    public User getUserbyID(int id) {
        return UserDB.getUserByID(database, id);
    }

    @Override
    public int getUserCount() { return UserDB.getUserCount(database); }

}

