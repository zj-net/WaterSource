package com.tristenallen.watersource.model;

/**
 * Created by tristen on 2/21/17.
 *
 * Base class for the backend model. Provides static access to individual managers and data access features.
 */
@SuppressWarnings("ClassWithTooManyDependents") // Model class forms backbone of backend
public final class Model {
    private static final AuthHelper AUTH_HELPER = AuthHelper.getInstance();
    private static final UserHelper USER_HELPER = UserHelper.getInstance();
    private static final ReportHelper REPORT_HELPER = ReportHelper.getInstance();
    private static int currentUserID = -1;
    private static User currentUser = null;

    private Model() {
        throw new RuntimeException("Static-only class.");
    }

    /**
     * Returns the model's AuthHelper.
     * @return AuthHelper object to be used for login.
     */
    public static AuthHelper getAuthHelper() {
        return AUTH_HELPER;
    }

    /**
     * Returns the model's AuthHelper.
     * @return AuthHelper object to be used for login.
     */
    public static UserHelper getUserHelper() {
        return USER_HELPER;
    }

    /**
     * Returns the model's ReportHelper.
     * @return ReportHelper object to be used for managing reports.
     */
    public static ReportHelper getReportHelper() {
        return REPORT_HELPER;
    }

    /**
     * Returns the ID of the currently logged in user.
     * @return int specifying the ID of this user.
     */
    public static int getCurrentUserID() {
        return currentUserID;
    }

    /**
     * Sets the currently logged in user's ID and associated User object.
     * @param currentUserID int specifying the ID of this user.
     */
    public static void setCurrentUser(int currentUserID, DataSource data) {
        Model.currentUserID = currentUserID;
        Model.currentUser = data.getUserbyID(currentUserID);
    }

    /**
     * Returns the currently logged in user.
     * @return User that is currently logged in.
     */
    public static User getCurrentUser() {
        return currentUser;
    }
}
