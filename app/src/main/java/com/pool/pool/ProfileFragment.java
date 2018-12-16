package com.pool.pool;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.pool.pool.model.Car;
import com.pool.pool.server.Server;
import com.pool.pool.server.Utils;

public class ProfileFragment extends Fragment implements OnMapReadyCallback {

    View mCont;
    Server mServer;
    GoogleMap mMap;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mCont = inflater.inflate(R.layout.fragment_profile, container, false);
        TextView name_e = (TextView) mCont.findViewById(R.id.pro_name);
        name_e.setText(Auth.getUser(getActivity()).getName());
        mServer = new Server(getActivity());
        mServer.getCar(Auth.getUser(getActivity()).getId(), new Utils.Callback<Car, String>() {
            @Override
            public void onSuccess(Car car) {
                TextView car_name_e = (TextView) mCont.findViewById(R.id.pro_car_name);
                car_name_e.setText(String.format("Car: %s", car.getName()));
            }

            @Override
            public void onFail(String obj) {

            }
        });
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.pro_map);
        mapFragment.getMapAsync(this);
        return mCont;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng location = Auth.getUser(getActivity()).getLocation();
        MarkerOptions marker = new MarkerOptions().position(location);
        mMap.addMarker(marker);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location,10f));
    }
}
