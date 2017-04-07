package com.tristenallen.watersource.model;

/**
 * Created by tristen on 2/21/17.
 * Contains user information.
 * Email and authorization level are not editable.
 */
public class User {
    private final String email;
    private final AuthLevel role;
    private String address;
    private String title;
    private String lastName;
    private String firstName;

    /**
     * Gets the user's email.
     * @return String email of the user.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets the user's authorization level.
     * @return AuthLevel authorization of the user.
     */
    public AuthLevel getRole() {
        return role;
    }

    /**
     * Gets the user's current address.
     * @return String specifying the current user address.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the user's address.
     * @param address String of the user's address.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the user's current title.
     * @return String specifying the user's title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the user's title.
     * @param title String indicating the User's title.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the user's last name.
     * @return String of the user's last name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the user's last name.
     * @param lastName String indicating the new last name.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Get the first name of the user.
     * @return String of the user's first name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the user's first name.
     * @param firstName String indicating the new first name.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Creates a new user with a blank profile.
     * @param email String specifying the email address of the user.
     * @param role AuthLevel specifying the access level of the user.
     * @param lastName String specifying the last name of the user.
     * @param firstName String specifying the first name of the user.
     */
    public User(String email, AuthLevel role, String lastName, String firstName) {
        this.email = email;
        this.role = role;
        this.lastName = lastName;
        this.firstName = firstName;
        address = "";
        title = "";
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
