package com.tristenallen.watersource.model;

import android.location.Location;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tristen on 2/25/2017.
 *
 * Contains methods for accessing and creating new kinds of reports.
 */
public class ReportHelper {
    private static ReportHelper INSTANCE = new ReportHelper();
    private int currentSourceReportNumber;
    private Map<Integer,SourceReport> sourceReports;

    /**
     * Creates a new ReportHelper with empty Maps
     * and initial report numbers of 1.
     */
    private ReportHelper() {
        currentSourceReportNumber = 1;
        sourceReports = new HashMap<>();
    }

    /**
     * Returns the instance of ReportHelper used in this application.
     * @return ReportHelper application instance.
     */
    protected static ReportHelper getInstance() {
        return INSTANCE;
    }

    /**
     * Returns the SourceReport associated with a specific report number.
     * Returns null if there is no such report.
     * @param id int specifying the report number to obtain.
     * @return SourceReport associated with the given report number.
     */
    public SourceReport getSourceReport(int id) {
        if (sourceReports.containsKey(id)) {
            return sourceReports.get(id);
        } else {
            return null;
        }
    }

    /**
     * Returns all of the source reports stored in the model as
     * a Collection.
     * @return Collection of all the source reports in the map.
     */
    public Collection<SourceReport> getSourceReports() {
        return sourceReports.values();
    }

    /**
     * Removes the SourceReport associated with the given ID number.
     * Returns false if there was no such report.
     *
     * Note that this does not free up the report number for future use.
     * ID numbers are one-use-only for the lifetime of the application.
     * @param id int specifying the report to delete.
     * @return boolean indicating the success of the deletion.
     */
    public boolean removeSourceReport(int id) {
        if (sourceReports.containsKey(id)) {
            sourceReports.remove(id);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Adds a new SourceReport to the ReportHelper with the given information.
     * Automatically generates a report ID number based on the order in which
     * reports are created.
     *
     * By convention, only the active user should be the author of new reports,
     * but this is not enforced in case of the need for adminsitrative tasks.
     *
     * @param user int of the user who is authoring this report.
     * @param location Location of the water in this report.
     * @param quality WaterQuality of the water.
     * @param type WaterType of the water.
     * @throws IllegalArgumentException if the given user ID is not a valid user.
     */
    public void addSourceReport(int user, Location location,
                                   WaterQuality quality, WaterType type) {
        if (UserHelper.getInstance().getUserByID(user) != null) {
            SourceReport newReport = new SourceReport(user, location, quality, type,
                    currentSourceReportNumber);
            sourceReports.put(currentSourceReportNumber++, newReport);
        } else {
            throw new IllegalArgumentException("You must pass in the ID of a"
            + " valid user!");
        }
    }
}
