package com.tristenallen.watersource.login;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;
import com.tristenallen.watersource.R;
import com.tristenallen.watersource.database.MyDatabase;
import com.tristenallen.watersource.model.AuthLevel;
import com.tristenallen.watersource.model.DataSource;
import com.tristenallen.watersource.model.Model;
import com.tristenallen.watersource.model.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jahziel on 2/22/17.
 * Activity for first time users to register on the app.
 */

public class RegistrationActivity extends AppCompatActivity {
    private EditText firstNameField;
    private EditText lastNameField;
    private EditText emailField;
    private EditText passwordField;
    private EditText confirmPassField;
    private Spinner authSpinner;
    private User user;
    private final Pattern emailPattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
            Pattern.CASE_INSENSITIVE);

    private DataSource data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        data = new MyDatabase(this);

        setContentView(R.layout.activity_registration);

        //-----------------getting text input------------------------
        firstNameField = (EditText) findViewById(R.id.firstNameTXT);
        lastNameField = (EditText) findViewById(R.id.lastNameTXT);
        emailField = (EditText) findViewById(R.id.emailTXT);
        passwordField = (EditText) findViewById(R.id.PasswordTXT);
        confirmPassField = (EditText) findViewById(R.id.confirmPassTXT);
        authSpinner = (Spinner) findViewById(R.id.authLVL);
        //--------------------------------------------------------------

        //populate spinner
        authSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                AuthLevel.values()));



        //movement from registration to main screen, text processing

        Button submitButton = (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            /**
             * This method verifies input data once the Submit button is pressed.
             * @param v the View for the activity
             */
            @Override
            public void onClick(View v) {
                //doing stuff with the text

                //get Strings and stuff
                String firstNameString = firstNameField.getText().toString();
                String lastNameString = lastNameField.getText().toString();
                String emailString = emailField.getText().toString();
                String passwordString = passwordField.getText().toString();
                String confirmPassString = confirmPassField.getText().toString();
                Matcher matcher = emailPattern.matcher(emailString);


                //verify input

                if (!passwordString.equals(confirmPassString)) {
                    Context context = getApplicationContext();
                    CharSequence error = "Your passwords don't match!";
                    int duration = Toast.LENGTH_LONG;
                    passwordField.setText("");
                    confirmPassField.setText("");
                    Toast badPass = Toast.makeText(context, error, duration);
                    badPass.show();
                } else if (!matcher.find()) {
                    //checks for email validity
                    Context context = getApplicationContext();
                    CharSequence error = "Invalid email!";
                    int duration = Toast.LENGTH_LONG;
                    emailField.setText("");
                    Toast badPass = Toast.makeText(context, error, duration);
                    badPass.show();
                } else {

                    //last part, after checks are passed
                    user = new User(emailString, (AuthLevel) authSpinner.getSelectedItem(), lastNameString, firstNameString);
                    //noinspection LawOfDemeter Model is the only way to get our singular instance of UserHelper
                    if (!Model.getUserHelper().addUser(user, emailString, passwordString, data)) {
                        Context context = getApplicationContext();
                        CharSequence error = "That email has already been used!";
                        int duration = Toast.LENGTH_LONG;
                        emailField.setText("");
                        Toast badPass = Toast.makeText(context, error, duration);
                        badPass.show();
                    }

                    Context context = getApplicationContext();
                    CharSequence msg = "Registration complete! Please login.";
                    int duration = Toast.LENGTH_LONG;
                    Toast badPass = Toast.makeText(context, msg, duration);
                    badPass.show();

                    //movement part
                    onBackPressed();
                }
            }
        });

    }



}

