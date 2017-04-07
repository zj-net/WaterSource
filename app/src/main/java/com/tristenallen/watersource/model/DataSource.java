package com.tristenallen.watersource.model;

import java.util.List;

/**
 * Created by David on 3/28/17.
 */


public interface DataSource {
    void createUser(int id, String password, String email,
                    AuthLevel role, String address, String title, String lastName,
                    String firstName);
    void deleteUser(int id);
    List<User> getAllUsers();
    boolean checkEmail(String email);
    boolean validate(String email, String password);
    int getIDbyEmail(String email);
    User getUserbyID(int id);
    int getUserCount();
}
