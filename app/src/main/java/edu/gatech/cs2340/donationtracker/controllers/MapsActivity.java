package edu.gatech.cs2340.donationtracker.controllers;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import edu.gatech.cs2340.donationtracker.R;
import edu.gatech.cs2340.donationtracker.model.Location;
import edu.gatech.cs2340.donationtracker.model.Model;

/**
 * this class contains all of the map activity
 */
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        FragmentManager map = getSupportFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment) map
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        /*LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
        for (Location location : Model.getLocationList()) {
            LatLng marker = new LatLng(Double.parseDouble(location.getLatitude()),
                    Double.parseDouble(location.getLongitude()));
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(marker);
            String name = location.getLocationName();
            markerOptions.title(name);
            mMap.addMarker(
                    markerOptions.snippet(
                            location.getPhoneNumber()));
        }
        final double atllat = 33.7490;
        final double atllong = -84.3880;
        LatLng atlanta = new LatLng(atllat, atllong);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(atlanta, 10));
    }
}
