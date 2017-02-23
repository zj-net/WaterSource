package com.tristenallen.watersource.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tristen on 2/13/17.
 */
public class AuthHelper {
    private static final AuthHelper INSTANCE = new AuthHelper();
    private UserHelper userHelper;
    private Map<Integer, String> passwords;

    private AuthHelper() {
        passwords = new HashMap<>();
        userHelper = UserHelper.getInstance();
    }

    protected static AuthHelper getInstance() {
        return INSTANCE;
    }

    /**
     * Attempts to authenticate a user given an email and password.
     * Returns an AuthOutcome object specifying the outcome of the operation.
     * @param email String specifying the user's email.
     * @param password String specifying the user's password.
     * @return AuthPackage containing the logged-in user and/or a status message
     * indiciating the failure of the operation.
     */
    public AuthPackage login(String email, String password) {
        if (-1 == userHelper.getIDbyEmail(email)) {
            return new AuthPackage(null, AuthStatus.INVALID_NAME);
        } else {
            int id = userHelper.getIDbyEmail(email);
            String validPass = passwords.get(id);
            User user = userHelper.getUserByID(id);

            if (password.equals(validPass)) {
                Model.setCurrentUser(id);
                return new AuthPackage(user, AuthStatus.VALID_LOGIN);
            } else {
                return new AuthPackage(user, AuthStatus.INVALID_PASSWORD);
            }
        }
    }

    /**
     * Adds a new user, by ID, to the internal password database.
     * Returns false if the ID does not exist in the user database.
     * @param id int ID of the user
     * @param password String specifying the user's password.
     * @return boolean indicating success of the operation.
     */
    protected boolean addUser(int id, String password) {
        if (userHelper.getUserByID(id) == null) {
            return false;
        } else {
            passwords.put(id, password);
            return true;
        }
    }
}
