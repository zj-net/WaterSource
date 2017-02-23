package com.tristenallen.watersource.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.tristenallen.watersource.R;
import com.tristenallen.watersource.model.Model;
import com.tristenallen.watersource.model.User;

/**
 * Created by David on 2/22/17.
 */

public class ViewProfileActivity extends AppCompatActivity {
    private TextView nameField;
    private TextView addressField;
    private TextView emailField;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewprofile);

        nameField = (TextView) findViewById(R.id.shownameTXT);
        addressField = (TextView) findViewById(R.id.showaddressTXT);
        emailField = (TextView) findViewById(R.id.showemailTXT);


        user = Model.getCurrentUser();

        nameField.setText(user.getFirstName()+ " " + user.getLastName());
        addressField.setText(user.getAddress());
        emailField.setText(user.getEmail());
    }

    protected void onEditPressed(View view) {
        Intent goToEditProfileActivity = new Intent(getApplicationContext(), EditProfileActivity.class);
        startActivity(goToEditProfileActivity);
        finish();
    }

    protected void onBackPressed(View view) {
        finish();
    }
}
