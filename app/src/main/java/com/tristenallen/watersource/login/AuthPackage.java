package com.tristenallen.watersource.login;

/**
 * Created by tristen on 2/13/17.
 * Describes login status and user information,
 * if valid.
 */
public class AuthPackage {
    private final int id;
    private final String username;
    private final AuthStatus status;

    /**
     * Constructs a new AuthPackage with the specified information.
     * Class is immutable.
     * @param id int specifying user ID. -1 if username is invalid.
     * @param username String specifying attempted username.
     * @param status AuthStatus specifying the failure point (or lack thereof) of the login.
     */
    public AuthPackage(int id, String username, AuthStatus status) {
        this.id = id;
        this.username = username;
        this.status = status;
    }

    public int getID() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public AuthStatus getStatus() {
        return status;
    }

}
