package com.tristenallen.watersource.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tristen on 2/21/17.
 *
 * Holds a list of users and provides methods for getting new users and adding new users.
 * Automatically assigns users an ID number and associates their email with that ID number.
 * Also stores a list of passwords for the users.
 */
public class UserHelper {
    private static final UserHelper INSTANCE = new UserHelper();
    private int currentID;
    private Map<Integer, User> userIDMap;
    private Map<String, Integer> emailIDMap;

    private UserHelper() {
        userIDMap = new HashMap<>();
        emailIDMap = new HashMap<>();
        currentID = 0;
    }

    protected static UserHelper getInstance() {
        return INSTANCE;
    }

    /**
     * Creates a new user from the given information. Returns false if there is already a user associated
     * with the given email.
     * @param user User containing the new user's information.
     * @param email String specifying the new user's login email.
     * @param password String specifying the new user's password.
     */
    public boolean addUser(User user, String email, String password) {
        if (emailIDMap.containsKey(email)) {
            return false;
        } else {
            emailIDMap.put(email, currentID);
            AuthHelper.getInstance().addUser(currentID, password);
            userIDMap.put(currentID, user);
            currentID++;
            return true;
        }
    }

    /**
     * Gets a user by their auto-generated ID number.
     * Returns null if there is no user by that ID.
     * @param id int specifying the user's ID.
     * @return User associated with that ID number.
     */
    public User getUserByID(int id) {
        return (userIDMap.containsKey(id)) ? userIDMap.get(id) : null;
    }

    /**
     * Gets a user's ID number by their login email address.
     * Returns -1 if there is no user associated with this ID.
     * @param email String specifying the login email of this user.
     * @return int ID associated with this email.
     */
    public int getIDbyEmail(String email) {
        return (emailIDMap.containsKey(email)) ? emailIDMap.get(email) : -1;
    }

}
