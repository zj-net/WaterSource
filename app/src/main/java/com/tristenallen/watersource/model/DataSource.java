package com.tristenallen.watersource.model;

import java.util.List;

/**
 * Created by David on 3/28/17.
 */


public interface DataSource {
    User createUser(int id, String password, String email,
                    AuthLevel role, String address, String title, String lastName,
                    String firstName);
    void deleteUser(int id);
    List<User> getAllUsers();
    public boolean checkEmail(String email);
    public boolean validate(String email, String password);
    public int getIDbyEmail(String email);
    public User getUserbyID(int id);
    public int getUserCount();
}
