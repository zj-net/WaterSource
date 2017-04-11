package com.tristenallen.watersource.model;

/**
 * Created by tristen on 2/13/17.
 * AuthHelper custom class
 */
public final class AuthHelper {
    private int currentUserID;
    private User currentUser;
    private static final AuthHelper INSTANCE = new AuthHelper();

    /**
     * Constructs a new AuthHelper with no current user ID and no current user.
     */
    private AuthHelper() {
        currentUserID = -1;
        currentUser = new User(null, null, null, null);
    }

    /**
     * Returns the singular instance of this class.
     * @return AuthHelper to be used throughout the app.
     */
    public static AuthHelper getInstance() {
        return INSTANCE;
    }

    /**
     * Attempts to authenticate a user given an email and password.
     * Returns an AuthOutcome object specifying the outcome of the operation.
     * @param email String specifying the user's email.
     * @param password String specifying the user's password.
     * @param data the DataSource
     * @return AuthPackage containing the logged-in user and/or a status message
     * indicating the failure of the operation.
     */
    @SuppressWarnings("FeatureEnvy")
    public AuthPackage login(String email, String password, DataSource data) {
        if (!data.checkEmail(email)) {
            return new AuthPackage(null, AuthStatus.INVALID_NAME);
        } else {
            int id = data.getIDbyEmail(email);
            if (data.validate(email,password)) {
                setCurrentUser(id,data);
                return new AuthPackage(data.getUserByID(id), AuthStatus.VALID_LOGIN);
            } else {
                return new AuthPackage(data.getUserByID(id), AuthStatus.INVALID_PASSWORD);
            }
        }
    }

    /**
     * Sets the currently logged in user's ID and associated User object.
     * @param currentUserID int specifying the ID of this user.
     * @param data the DataSource object used for getting data from database.
     */
    public void setCurrentUser(int currentUserID, DataSource data) {
        this.currentUserID = currentUserID;
        this.currentUser = data.getUserByID(currentUserID);
    }

    /**
     * Returns the ID of the currently logged in user.
     * @return int specifying the ID of this user.
     */
    public int getCurrentUserID() {
        return currentUserID;
    }

    /**
     * Returns the currently logged in user.
     * @return User that is currently logged in.
     */
    public User getCurrentUser() {
        return currentUser;
    }

}
