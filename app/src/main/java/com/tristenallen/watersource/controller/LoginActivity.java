package com.tristenallen.watersource.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.tristenallen.watersource.R;
import com.tristenallen.watersource.database.MyDatabase;
import com.tristenallen.watersource.controller.MainActivity;
import com.tristenallen.watersource.model.*;


public class LoginActivity extends AppCompatActivity {
    private EditText usrname;
    private EditText password;
    private AuthHelper verifier;
    private AuthPackage AP;
    private AuthStatus status;

    private DataSource data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        data = new MyDatabase(this);

        setContentView(R.layout.activity_login);

        usrname = (EditText) findViewById(R.id.editText);
        password = (EditText) findViewById(R.id.editText2);


        Button loginButton = (Button) findViewById(R.id.LOGIN_BUTTON);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usrnameStr = usrname.getText().toString();
                String passwordStr = password.getText().toString();
                verifier = Model.getAuthHelper();
                AP = verifier.login(usrnameStr, passwordStr, data);
                status = AP.getStatus();

                if (status == AuthStatus.INVALID_NAME) {
                    Context context = getApplicationContext();
                    CharSequence error = "Invalid UserName!";
                    int duration = Toast.LENGTH_LONG;
                    usrname.setText("");
                    password.setText("");
                    Toast badPass = Toast.makeText(context, error, duration);
                    badPass.show();
                } else if (status == AuthStatus.INVALID_PASSWORD) {
                    Context context = getApplicationContext();
                    CharSequence error = "Invalid password!";
                    int duration = Toast.LENGTH_LONG;
                    password.setText("");
                    Toast badPass = Toast.makeText(context, error, duration);
                    badPass.show();

                } else {
                    Intent goToMainActivity = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(goToMainActivity);
                }
            }
        });
    }
}