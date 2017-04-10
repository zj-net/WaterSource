package com.tristenallen.watersource.model;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Tristen on 2/25/2017.
 *
 * Contains methods for accessing and creating new kinds of reports.
 */
public final class ReportHelper {
    private static final ReportHelper INSTANCE = new ReportHelper();
    private static final String QUALITY_DB = "QualityReports";
    private static final String PURITY_DB = "PurityReports";
    private final Gson gson;

    /**
     * Creates a new ReportHelper.
     */
    private ReportHelper() {
        gson = new Gson();
    }

    /**
     * Returns the instance of ReportHelper used in this application.
     * @return ReportHelper application instance.
     */
    static ReportHelper getInstance() {
        return INSTANCE;
    }

    /**
     * Returns the SourceReport associated with a specific report number.
     * Returns null if there is no such report.
     * @param id int specifying the report number to obtain.
     * @param context Activity requesting the report.
     * @return SourceReport associated with the given report number.
     */
    public SourceReport getSourceReport(int id, Activity context) {
        SharedPreferences sourceReports = context.getSharedPreferences(QUALITY_DB, Context.MODE_PRIVATE);
        if (sourceReports.contains(String.valueOf(id))) {
            String reportString = sourceReports.getString(String.valueOf(id), null);
            return gson.fromJson(reportString, SourceReport.class);
        } else {
            return null;
        }
    }

    /**
     * Returns all of the source reports stored in the model as
     * a Collection.
     * @param context Activity requesting the report.
     * @return Collection of all the source reports in the model.
     */
    public Collection<SourceReport> getSourceReports(Activity context) {
        SharedPreferences sourceReports = context.getSharedPreferences(QUALITY_DB, Context.MODE_PRIVATE);
        Collection<SourceReport> reportList = new ArrayList<>();

        for (Object o: sourceReports.getAll().values()) {
            reportList.add(gson.fromJson((String) o, SourceReport.class));
        }

        return reportList;
    }

    /**
     * Returns all of the purity reports stored in the model as
     * a Collection.
     * @param context Activity requesting the report.
     * @return Collection of all the purity reports in the model.
     */
    public Collection<PurityReport> getPurityReports(Activity context) {
        SharedPreferences sourceReports = context.getSharedPreferences(PURITY_DB, Context.MODE_PRIVATE);
        Collection<PurityReport> reportList = new ArrayList<>();

        for (Object o: sourceReports.getAll().values()) {
            reportList.add(gson.fromJson((String) o, PurityReport.class));
        }


        return reportList;
    }

    /**
     * Adds a new SourceReport to the ReportHelper with the given information.
     * Automatically generates a report ID number based on the order in which
     * reports are created.
     *
     * By convention, only the active user should be the author of new reports,
     * but this is not enforced in case of the need for administrative tasks.
     *
     * @param user int of the user who is authoring this report.
     * @param location Location of the water in this report.
     * @param quality WaterQuality of the water.
     * @param type WaterType of the water.
     * @param context Activity adding the report.
     * @param data the DataSource object used to get saved data.
     * @throws IllegalArgumentException if the given user ID is not a valid user.
     */
    public void addSourceReport(int user, Location location,
                                   WaterQuality quality, WaterType type, DataSource data, Activity context) {
        if (data.getUserbyID(user) != null) {
            int currentSourceReportNumber = getSourceReports(context).size() + 1;
            SourceReport newReport = new SourceReport(user, location, quality, type,
                    currentSourceReportNumber);
            SharedPreferences sourceReports = context.getSharedPreferences(QUALITY_DB, Context.MODE_PRIVATE);
            String reportString = gson.toJson(newReport);
            sourceReports.edit().putString(String.valueOf(currentSourceReportNumber), reportString).apply();
        } else {
            throw new IllegalArgumentException("You must pass in the ID of a"
            + " valid user!");
        }
    }

    /**
     * Adds a new PurityReport to the ReportHelper with the given information.
     * Automatically generates a report ID number based on the order in which
     * reports are created.
     *
     * By convention, only the active user should be the author of new reports,
     * but this is not enforced in case of the need for administrative tasks.
     *
     * @param user int of the user who is authoring this report.
     * @param location Location of the water in this report.
     * @param purity WaterPurity of the water.
     * @param virusPPM int specifying viruses in ppm.
     * @param contaminantPPM int specifying contaminants in ppm.
     * @param context Activity adding the report.
     * @param data the DataSource object used to get data from the database.
     * @throws IllegalArgumentException if the given user ID is not a valid user.
     */
    public void addPurityReport(int user, Location location,
                                WaterPurity purity, int virusPPM, int contaminantPPM,
                                DataSource data, Activity context) {
        int currentPurityReportNumber = getPurityReports(context).size() + 1;
        if (data.getUserbyID(user) != null) {
            PurityReport newReport = new PurityReport(user, location, purity, currentPurityReportNumber, virusPPM,
                    contaminantPPM);
            SharedPreferences purityReports = context.getSharedPreferences(PURITY_DB, Context.MODE_PRIVATE);
            String reportString = gson.toJson(newReport);
            purityReports.edit().putString(String.valueOf(currentPurityReportNumber), reportString).apply();
        } else {
            throw new IllegalArgumentException("You must pass in the ID of a"
                    + " valid user!");
        }
    }
}
