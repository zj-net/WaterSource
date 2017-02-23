package com.tristenallen.watersource.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import com.tristenallen.watersource.R;
import com.tristenallen.watersource.model.Model;
import com.tristenallen.watersource.model.User;

/**
 * Created by David on 2/22/17.
 */

public class EditProfileActivity extends AppCompatActivity {
    private EditText nameField;
    private EditText addressField;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        nameField = (EditText) findViewById(R.id.nameTXT);
        addressField = (EditText) findViewById(R.id.addressTXT);

        user = Model.getCurrentUser();

        nameField.setText(user.getFirstName()+ " " + user.getLastName());
        addressField.setText(user.getAddress());
    }
}
