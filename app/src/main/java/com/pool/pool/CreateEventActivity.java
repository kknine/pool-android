package com.pool.pool;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.pool.pool.model.Event;
import com.pool.pool.server.Constants;
import com.pool.pool.server.Server;
import com.pool.pool.server.Utils;

import static com.pool.pool.Constants.EXTRA_EVENT_ID;
import static com.pool.pool.Constants.EXTRA_EVENT_NAME;
import static com.pool.pool.Constants.EXTRA_EVENT_OWNER;

public class CreateEventActivity extends AppCompatActivity implements OnMapReadyCallback {

    Server mServer;
    GoogleMap mMap;
    LatLng mLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        Button create_b = findViewById(R.id.cev_create);
        create_b.setEnabled(false);
        mServer = new Server(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.cev_toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayShowTitleEnabled(false);
        ab.setDisplayHomeAsUpEnabled(true);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.cev_map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    public void onFind(View v) {
        EditText location_e = (EditText) findViewById(R.id.cev_location);
        String location = location_e.getText().toString();
        mServer.getLatLngForLocation(location, new Utils.Callback<LatLng, String>() {
            @Override
            public void onSuccess(LatLng position) {
                mMap.clear();
                MarkerOptions marker = new MarkerOptions().position(position);
                mMap.addMarker(marker);
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position,10f));
                Button create_b = findViewById(R.id.cev_create);
                create_b.setEnabled(true);
                mLocation = position;
            }

            @Override
            public void onFail(String obj) {
                Toast.makeText(CreateEventActivity.this,"Can't get the location",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void createEvent(View v) {
        EditText name_e = (EditText) findViewById(R.id.cev_name);
        EditText datetime_e = (EditText) findViewById(R.id.cev_datetime);
        final Event event = new Event(-1,name_e.getText().toString(),mLocation,datetime_e.getText().toString(),Auth.getUser(this).getId());
        mServer.createNewEvent(event, new Utils.Callback<Integer,String>() {
            @Override
            public void onSuccess(final Integer eventId) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CreateEventActivity.this);
                builder.setTitle("Event created!");
                builder.setMessage("You can now invite your friends");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(CreateEventActivity.this,EventDetailsActivity.class);
                        intent.putExtra(EXTRA_EVENT_ID,eventId);
                        intent.putExtra(EXTRA_EVENT_NAME,event.getName());
                        intent.putExtra(EXTRA_EVENT_OWNER,event.getOwnerId());
                        startActivity(intent);
                        finish();
                    }
                });
                builder.show();
            }

            @Override
            public void onFail(String obj) {

            }
        });

    }
}
