package com.tristenallen.watersource;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.tristenallen.watersource.login.LoginActivity;

public class LaunchActivity extends AppCompatActivity {
    private Button login;
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToLoginScreen = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(goToLoginScreen);
            }
        });
//        this isn't getting implemented yet, but I don't want to type it again
//        register = (Button) findViewById(R.layout.activity_signin);
//        register.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent goToSignUpScreen = new Intent(LaunchActivity.this)
//            }
//        });
    }
}
