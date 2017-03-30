package com.tristenallen.watersource.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.tristenallen.watersource.model.AuthLevel;
import com.tristenallen.watersource.model.DataSource;
import com.tristenallen.watersource.model.User;

import java.util.List;

/**
 * Created by David on 3/28/17.
 */

public class MyDatabase implements DataSource {


    private SQLiteDatabase database;
    private MyDBHelper dbHelper;


    public MyDatabase(Context context) {
        dbHelper = new MyDBHelper(context);
        open();
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    @Override
    public User createUser(int id, String password, String email,
                           AuthLevel role, String address, String title, String lastName,
                           String firstName) {
        return UserDB.create(database, id, password, email, role, address, title, lastName,
                firstName);

    }

    @Override
    public void deleteUser(int id) {
        UserDB.delete(database, id);
    }

    @Override
    public List<User> getAllUsers() {
        return UserDB.getAllUsers(database);
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

