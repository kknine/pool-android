package com.pool.pool;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.pool.pool.model.User;
import com.pool.pool.server.Server;
import com.pool.pool.server.Utils;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private int eventId;
    Server mServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        eventId = getIntent().getIntExtra(Constants.EXTRA_EVENT_ID,0);
        mServer = new Server(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mServer.getPolylines(eventId, new Utils.Callback<ArrayList<PolylineOptions>, String>() {
            @Override
            public void onSuccess(ArrayList<PolylineOptions> polies) {
                for(PolylineOptions poly : polies) {
                    mMap.addPolyline(poly);
                }
                mServer.getInvitedGuests(eventId, new Utils.Callback<ArrayList<User>, String>() {
                    @Override
                    public void onSuccess(ArrayList<User> list) {
                        ArrayList<LatLng> points = new ArrayList<>();
                        for(User user : list) {
                            MarkerOptions marker = new MarkerOptions().position(user.getLocation());
                            mMap.addMarker(marker);
                            points.add(user.getLocation());
                        }
                        centerCamera(points);

                    }

                    @Override
                    public void onFail(String obj) {

                    }
                });
            }

            @Override
            public void onFail(String obj) {

            }
        });


        // Add a marker in Sydney and move the camera

    }

    private void centerCamera(ArrayList<LatLng> points) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (LatLng point : points) {
            builder.include(point);
        }
        LatLngBounds bounds = builder.build();
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        int padding = (int) (width * 0.2); // offset from edges of the map 20% of screen
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding),1000,null);
    }
}
