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

    /**
     * constructor method to create a database
     * @param context Context the activity context
     */
    public MyDatabase(Context context) {
        dbHelper = new MyDBHelper(context);
        open();
    }

    private void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    /**
     * wrapper method to insert a new user to the database
     * @param id int new user assigned id
     * @param password String password for the user
     * @param email String email for the user
     * @param role AuthLevel user auth level: user,worker,manager,admin
     * @param address String address of the user
     * @param title String title of the user
     * @param lastName String lastName of the user
     * @param firstName String firstName of the user
     */
    @Override
    public void createUser(int id, String password, String email,
                           AuthLevel role, String address, String title, String lastName,
                           String firstName) {
        UserDB.create(database, id, password, email, role, address, title, lastName,
                firstName);
    }

    /**
     * wrapper method for check email
     * @param email String email for the user
     * @return true if the email already exist in User DB, false otherwise
     */
    @Override
    public boolean checkEmail(String email) {
        return UserDB.checkEmail(database, email);
    }

    /**
     * wrapper method for validate email and password
     * @param email String email for the user
     * @param password String password for the user
     * @return true if the email matches password, false otherwise
     */
    @Override
    public boolean validate(String email, String password) {
        return UserDB.validate(database, email, password);
    }

    /**
     * wrapper method for get the id of user from email
     * @param email String email for the user
     * @return the id int of the user
     */
    @Override
    public  int getIDbyEmail(String email) {
        return UserDB.getIDbyEmail(database, email);
    }

    /**
     * wrapper method for get the user from id
     * @param id int id of the user
     * @return User the user of the id
     */
    @Override
    public User getUserbyID(int id) {
        return UserDB.getUserByID(database, id);
    }

    /**
     * wrapper method for get the number of users from DB
     * @return int the number of user
     */
    @Override
    public int getUserCount() { return UserDB.getUserCount(database); }

}

