package com.tristenallen.watersource.model;

import android.location.Location;

import java.util.Date;
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

    SourceReport createSourceReport(int reportID, int userID, Date timestamp,
                                    Location location, WaterType type, WaterQuality quality);
    public int getSourceReportCount();
    List<SourceReport> getAllSourceReports();

    PurityReport createPurityReport(int reportID, int userID, Date timestamp, Location location,
                                    WaterPurity purity, int virusPPM, int contaminantPPM);
    public int getPurityReportCount();
    List<PurityReport> getAllPurityReports();
}
