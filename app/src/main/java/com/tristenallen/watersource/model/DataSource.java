package com.tristenallen.watersource.model;

/**
 * Created by David on 3/28/17.
 * Interface for performing retrieval and update method.
 */


public interface DataSource {

    /**
     * method for add user to the db
     * @param id int new user assigned id
     * @param password String password for the user
     * @param email String email for the user
     * @param role AuthLevel user auth level: user,worker,manager,admin
     * @param address String address of the user
     * @param title String title of the user
     * @param lastName String lastName of the user
     * @param firstName String firstName of the user
     */
    void createUser(int id, String password, String email,
                    AuthLevel role, String address, String title, String lastName,
                    String firstName);

    /**
     * method for check email
     * @param email String email for the user
     * @return true if the email already exist in User DB, false otherwise
     */
    boolean checkEmail(String email);

    /**
     * method for validate email and password
     * @param email String email for the user
     * @param password String password for the user
     * @return true if the email matches password, false otherwise
     */
    boolean validate(String email, String password);

    /**
     * method for get the id of user from email
     * @param email String email for the user
     * @return the id int of the user
     */
    int getIDbyEmail(String email);

    /**
     * method for get the user from id
     * @param id int id of the user
     * @return User the user of the id
     */
    User getUserbyID(int id);

    /**
     * method for get the number of users from DB
     * @return int the number of user
     */
    int getUserCount();
}
