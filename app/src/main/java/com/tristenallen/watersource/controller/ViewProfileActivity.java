package com.tristenallen.watersource.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import com.tristenallen.watersource.R;
import com.tristenallen.watersource.model.AuthHelper;
import com.tristenallen.watersource.model.User;

import java.util.Locale;

/**
 * Created by David on 2/22/17.
 * Activity for user to view their profile information.
 */

public class ViewProfileActivity extends AppCompatActivity {

    @SuppressWarnings({"FeatureEnvy", "LawOfDemeter"})
    // feature envy smell occurs because User is a data holder class
    // Law of Demeter inspection error also occurs because of User being a data holder
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewprofile);

        TextView nameField = (TextView) findViewById(R.id.shownameTXT);
        TextView addressField = (TextView) findViewById(R.id.showaddressTXT);
        TextView emailField = (TextView) findViewById(R.id.showemailTXT);

        AuthHelper authHelper = AuthHelper.getInstance();

        User user = authHelper.getCurrentUser();

        nameField.setText(String.format(Locale.US, "%s %s %s",
                user.getTitle(), user.getFirstName(), user.getLastName()));
        addressField.setText(user.getAddress());
        emailField.setText(user.getEmail());
    }

    /**
     * Method for handling user pressing the "Edit" button.
     * @param view the View object required by Android for this method to run.
     */
    @SuppressWarnings("UnusedParameters")
    //View view is required by android
    public void onEditPressed(View view) {
        Intent goToEditProfileActivity = new Intent(getApplicationContext(),
                EditProfileActivity.class);
        startActivity(goToEditProfileActivity);
    }

    /**
     * Method for handling the user pressing the "back" button.
     * @param view the View object required by Adroid for this method to run.
     */
    @SuppressWarnings("UnusedParameters")
    //View view is required by android
    public void onBackPressed(View view) {
        super.onBackPressed();
    }
}
