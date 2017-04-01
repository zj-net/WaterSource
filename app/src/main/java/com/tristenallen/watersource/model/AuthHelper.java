package com.tristenallen.watersource.model;

/**
 * Created by tristen on 2/13/17.
 */
public class AuthHelper {
    private static final AuthHelper INSTANCE = new AuthHelper();

    public static AuthHelper getInstance() {
        return INSTANCE;
    }

    /**
     * Attempts to authenticate a user given an email and password.
     * Returns an AuthOutcome object specifying the outcome of the operation.
     * @param email String specifying the user's email.
     * @param password String specifying the user's password.
     * @return AuthPackage containing the logged-in user and/or a status message
     * indicating the failure of the operation.
     */
    public AuthPackage login(String email, String password, DataSource data) {
        if (!data.checkEmail(email)) {
            return new AuthPackage(null, AuthStatus.INVALID_NAME);
        } else {
            int id = data.getIDbyEmail(email);
            if (data.validate(email,password)) {
                Model.setCurrentUser(id,data);
                return new AuthPackage(data.getUserbyID(id), AuthStatus.VALID_LOGIN);
            } else {
                return new AuthPackage(data.getUserbyID(id), AuthStatus.INVALID_PASSWORD);
            }
        }
    }

}
