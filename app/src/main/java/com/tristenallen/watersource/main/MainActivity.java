package com.tristenallen.watersource.main;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.tristenallen.watersource.LaunchActivity;
import com.tristenallen.watersource.R;
import com.tristenallen.watersource.controller.*;
import com.tristenallen.watersource.database.MyDatabase;
import com.tristenallen.watersource.model.AuthLevel;
import com.tristenallen.watersource.model.DataSource;
import com.tristenallen.watersource.model.Model;
import com.tristenallen.watersource.model.PurityReport;
import com.tristenallen.watersource.model.ReportHelper;
import com.tristenallen.watersource.model.SourceReport;
import com.tristenallen.watersource.model.User;
import com.tristenallen.watersource.reports.SubmitH20SourceReportActivity;

import java.util.ArrayList;
import java.util.Collection;

@SuppressWarnings("ClassWithTooManyDependencies") // MainActivity must include numerous dependencies due to Android
public class MainActivity extends AppCompatActivity implements
        LogoutDialogFragment.LogoutDialogListener,OnMapReadyCallback {

    private GoogleMap mMap;
    private ReportHelper reportHelper;
    public static final String ARG_latLng = "latLng";
    private Marker selectionMarker;
    private final Collection<Marker> purityMarkers = new ArrayList<>();
    private static final float MARKER_ALPHA = 0.5f;


    private User user;

    private DataSource data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        data = new MyDatabase(this);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("WaterSource");
        setSupportActionBar(toolbar);

        Button viewReportList = (Button) findViewById(R.id.viewReports);

        viewReportList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToReportList = new Intent(getApplicationContext(), ViewReportsActivity.class);
                startActivity(goToReportList);
            }
        });

        @SuppressWarnings("ChainedMethodCall") // we do not use the SupportFragmentManager more than once.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        reportHelper = Model.getReportHelper();

        user = Model.getCurrentUser();

        //jve.
        Button viewPurityReportButton = (Button) findViewById(R.id.viewPurityReportsButton);
        if ((user.getRole() == AuthLevel.MANAGER) || (user.getRole() == AuthLevel.ADMIN)) {
            viewPurityReportButton.setVisibility(View.VISIBLE);
            viewPurityReportButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent goToPurityReportList = new Intent(getApplicationContext(), ViewPurityReportsActivity.class);
                    startActivity(goToPurityReportList);
                }
            });
        } else {
            viewPurityReportButton.setVisibility(View.INVISIBLE);
        }

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
    public void onDialogNegativeClick() {
        //do nothing
    }

    @Override
    public void onDialogPositiveClick() {
        Model.setCurrentUser(-1,data);
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

    @SuppressWarnings("FeatureEnvy") // feature envy smell occurs because of how the map is constructed
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Setting a click event handler for the map
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng latLng) {

                if (null != selectionMarker) {
                    selectionMarker.remove();
                }

                // Creating a marker
                MarkerOptions markerOptions = new MarkerOptions();

                // Setting the position for the marker
                markerOptions.position(latLng);

                // Setting the title for the marker.
                // This will be displayed on taping the marker
                markerOptions.title("Water Source");
                markerOptions.draggable(true);
                markerOptions.alpha(MARKER_ALPHA);

                // Animating to the touched position
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                // Placing a marker on the touched position
                selectionMarker = mMap.addMarker(markerOptions);
            }
        });

        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener(){
            @Override
            public boolean onMarkerClick(Marker marker) {
                //if the marker is newly added show submit report screen
                if (marker.getAlpha() == MARKER_ALPHA) {
                    if (user.getRole() == AuthLevel.USER){
                        Intent goToSubmitSourceActivity = new Intent(getApplicationContext(),
                                SubmitH20SourceReportActivity.class);
                        goToSubmitSourceActivity.putExtra(MainActivity.ARG_latLng,marker.getPosition());
                        startActivity(goToSubmitSourceActivity);
                    } else {
                        Intent goToSubmitPurityActivity = new Intent(getApplicationContext(),
                                SubmitPurityReportActivity.class);
                        goToSubmitPurityActivity.putExtra(MainActivity.ARG_latLng,marker.getPosition());
                        startActivity(goToSubmitPurityActivity);
                    }
                    return true;
                }
                // if the marker is not newly added, show info window
                return false;

            }

        });

        Collection<SourceReport> reportList = reportHelper.getSourceReports(this);
        for (SourceReport r : reportList) {
            Location reportLocation = r.getLocation();
            LatLng loc = new LatLng(reportLocation.getLatitude(), reportLocation.getLongitude());
            //noinspection ChainedMethodCall Necessary due to how MarkerOptions() works
            mMap.addMarker(new MarkerOptions().position(loc).title("Water Source")
                    .snippet("Type: "+ r.getType() + "\n" + "Quality: " + r.getQuality())
                    .zIndex(r.getReportNumber()));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
        }

        //noinspection ChainedMethodCall only time this role is used in this statement
        if (user.getRole().compareTo(AuthLevel.USER) > 0) {
            Collection<PurityReport> purityReportList = reportHelper.getPurityReports(this);
            for (PurityReport r : purityReportList) {
                Location reportLocation = r.getLocation();
                LatLng loc = new LatLng(reportLocation.getLatitude(), reportLocation.getLongitude());
                String s = "Condition: " + r.getPurity() + "\n" + "VirusPPM: " + r.getVirusPPM() + "\n"
                        + "ContaminantPPM: " + r.getContaminantPPM() + "\nLong press for more!";
                Marker newMarker = mMap.addMarker(new MarkerOptions().position(loc)
                        .title("Water Purity").snippet(s)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                        .zIndex(r.getReportNumber()));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
                purityMarkers.add(newMarker);
            }
        }

        mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter());

        if (user.getRole().compareTo(AuthLevel.MANAGER) >= 0) {
            mMap.setOnInfoWindowLongClickListener(new GoogleMap.OnInfoWindowLongClickListener() {
                @Override
                public void onInfoWindowLongClick(Marker marker) {
                    if (purityMarkers.contains(marker)) {
                        onInfoWindowClick(marker);
                    }
                }
            });
        }



    }

    private void onInfoWindowClick(Marker marker) {
        Intent goToPRList = new Intent(getApplicationContext(), ViewLocationPurityReportsActivity.class);
        LatLng position = marker.getPosition();
        double[] latlng = {position.latitude, position.longitude};
        goToPRList.putExtra("Location", latlng);
        startActivity(goToPRList);
    }

    class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

        private final View myContentsView;

        CustomInfoWindowAdapter(){
            myContentsView = getLayoutInflater().inflate(R.layout.marker_info_content, (ViewGroup)null);
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
            return null;
        }


    }

}