package com.tristenallen.watersource.main;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.tristenallen.watersource.R;

public class MainActivity extends AppCompatActivity implements
        LogoutDialogFragment.LogoutDialogListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater infalter = getMenuInflater();
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.action_logout:
                // User chose the "Logout" action, log the user out
                logout();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onBackPressed() {
        logout();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        //do nothing
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        super.onBackPressed();
    }

    // launches a dialog confirming that the user wants to log out
    private void logout() {
        LogoutDialogFragment logout = new LogoutDialogFragment();
        logout.show(getSupportFragmentManager(), "LogoutDialogFragment");
    }
}
