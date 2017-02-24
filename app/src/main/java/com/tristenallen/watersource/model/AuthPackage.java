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

    public User getUser() {
        return user;
    }

    public AuthStatus getStatus() {
        return status;
    }

}
