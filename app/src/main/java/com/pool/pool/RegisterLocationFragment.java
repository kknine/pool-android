package com.pool.pool;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.pool.pool.server.Server;
import com.pool.pool.server.Utils;

public class RegisterLocationFragment extends Fragment implements View.OnClickListener, OnMapReadyCallback {

    private OnFragmentInteractionListener mListener;
    View cont;
    GoogleMap mMap;
    Server mServer;
    LatLng mLocation;
    public RegisterLocationFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        cont = inflater.inflate(R.layout.fragment_register_location, container, false);
        Button b = (Button) cont.findViewById(R.id.rlo_save);
        b.setOnClickListener(this);
        Button find = (Button) cont.findViewById(R.id.rlo_find);
        find.setOnClickListener(this);
        final EditText email_e = (EditText) cont.findViewById(R.id.rlo_location);
        email_e.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    onClick(email_e);
                    handled = true;
                }
                return handled;
            }
        });
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.rlo_map);
        mapFragment.getMapAsync(this);
        mServer = new Server(getActivity());
        return cont;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.rlo_save) {
            EditText location_e = (EditText) cont.findViewById(R.id.rlo_location);
            String email = location_e.getText().toString();
            mListener.locationSaved(email);
        } else if(v.getId()==R.id.rlo_find) {
            EditText location_e = (EditText) cont.findViewById(R.id.rlo_location);
            String location = location_e.getText().toString();
            mServer.getLatLngForLocation(location, new Utils.Callback<LatLng, String>() {
                @Override
                public void onSuccess(LatLng position) {
                    mMap.clear();
                    MarkerOptions marker = new MarkerOptions().position(position);
                    mMap.addMarker(marker);
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position,10f));
                    Button continue_b = cont.findViewById(R.id.rlo_save);
                    continue_b.setEnabled(true);
                    mLocation = position;
                }

                @Override
                public void onFail(String obj) {
                    Toast.makeText(getContext(),"Can't get the location",Toast.LENGTH_SHORT).show();
                }
            });

        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

    }


    public interface OnFragmentInteractionListener {
        void locationSaved(String location);
    }
}
