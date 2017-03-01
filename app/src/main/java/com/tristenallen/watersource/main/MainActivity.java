package com.tristenallen.watersource.main;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tristenallen.watersource.LaunchActivity;
import com.tristenallen.watersource.R;
import com.tristenallen.watersource.model.Model;
import com.tristenallen.watersource.controller.ViewProfileActivity;
import com.tristenallen.watersource.model.ReportHelper;
import com.tristenallen.watersource.model.SourceReport;
import com.tristenallen.watersource.model.WaterQuality;
import com.tristenallen.watersource.model.WaterType;
import com.tristenallen.watersource.reports.submitH20SourceReportActivity;

import java.util.Collection;

public class MainActivity extends AppCompatActivity implements
        LogoutDialogFragment.LogoutDialogListener,OnMapReadyCallback {

    private GoogleMap mMap;
    private ReportHelper reportHelper;
    public static final String ARG_latLng = "latLng";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        reportHelper = Model.getReportHelper();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar, menu);
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

            case R.id.action_profile:
                showProfile();
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
        Model.setCurrentUser(-1);
        Intent goToLaunchActivity = new Intent(getApplicationContext(), LaunchActivity.class);
        goToLaunchActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(goToLaunchActivity);
    }

    // launches a dialog confirming that the user wants to log out
    private void logout() {
        LogoutDialogFragment logout = new LogoutDialogFragment();
        logout.show(getSupportFragmentManager(), "LogoutDialogFragment");
    }

    // launches a dialog confirming that the user wants to log out
    private void showProfile() {
        Intent goToEditProfileActivity = new Intent(getApplicationContext(), ViewProfileActivity.class);
        startActivity(goToEditProfileActivity);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Setting a click event handler for the map
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng latLng) {

                // Creating a marker
                MarkerOptions markerOptions = new MarkerOptions();

                // Setting the position for the marker
                markerOptions.position(latLng);

                Location loc = new Location("Water Report Location");
                loc.setLatitude(latLng.latitude);
                loc.setLongitude(latLng.longitude);

                reportHelper.addSourceReport(Model.getCurrentUserID(),loc, WaterQuality.UNKNOWN, WaterType.UNKNOWN);

                //Log.d("map",latLng.toString());
                int lastReportID = reportHelper.getSourceReports().size();
                SourceReport lastReport = reportHelper.getSourceReport(lastReportID);

                // Setting the title for the marker.
                // This will be displayed on taping the marker
                markerOptions.title("Water Source");
                markerOptions.snippet("Quality: " + lastReport.getQuality() + "\n" + "Type: " + lastReport.getType());
                markerOptions.draggable(true);
                markerOptions.alpha(0.5f);

                // Animating to the touched position
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                // Placing a marker on the touched position
                mMap.addMarker(markerOptions);
            }
        });

        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener(){
            @Override
            public boolean onMarkerClick(Marker marker) {
                //if the marker is newly added show submit report screen
                if (marker.getAlpha() == .5f) {
                    Intent goToSubmitActivity = new Intent(getApplicationContext(), submitH20SourceReportActivity.class);
                    goToSubmitActivity.putExtra(MainActivity.ARG_latLng,marker.getPosition());
                    startActivity(goToSubmitActivity);

                    return true;
                }
                // if the marker is not newly added, show info window
                return false;

            }

        });

        Collection<SourceReport> reportList = reportHelper.getSourceReports();
        for (SourceReport r : reportList) {
            LatLng loc = new LatLng(r.getLocation().getLatitude(), r.getLocation().getLongitude());
            mMap.addMarker(new MarkerOptions().position(loc).title("Water Source").snippet("Type: "+ r.getType() + "\n" + "Quality: " + r.getQuality()));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
        }


        mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter());


    }

    class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

        private final View myContentsView;

        CustomInfoWindowAdapter(){
            myContentsView = getLayoutInflater().inflate(R.layout.marker_info_content, null);
        }

        @Override
        public View getInfoContents(Marker marker) {

            TextView tvTitle = ((TextView)myContentsView.findViewById(R.id.title));
            tvTitle.setText(marker.getTitle());
            TextView tvSnippet = ((TextView)myContentsView.findViewById(R.id.snippet));
            tvSnippet.setText(marker.getSnippet());

            return myContentsView;
        }

        @Override
        public View getInfoWindow(Marker marker) {
            // TODO Auto-generated method stub
            return null;
        }

    }

}



