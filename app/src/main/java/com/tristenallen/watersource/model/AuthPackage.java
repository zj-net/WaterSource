package com.tristenallen.watersource.model;

/**
 * Created by tristen on 2/13/17.
 * Describes login status and user information,
 * if valid.
 */
public class AuthPackage {
    private final User user;
    private final AuthStatus status;

    /**
     * Constructs a new AuthPackage with the specified information.
     * Class is immutable.
     * @param user User object that was attempted to be logged in. null if username is invalid.
     * @param status AuthStatus specifying the failure point (or lack thereof) of the login.
     */
    public AuthPackage(User user, AuthStatus status) {
        this.user = user;
        this.status = status;
    }

    /**
     * Method for getting the user associated with this AuthPackage.
     * @return this AuthPackage's User.
     */
    public User getUser() {
        return user;
    }

    /**
     * Method for getting the status of this AuthPackage.
     * @return the AuthPackage's AuthStatus; useful for checking if there was a login problem.
     */
    public AuthStatus getStatus() {
        return status;
    }

}
