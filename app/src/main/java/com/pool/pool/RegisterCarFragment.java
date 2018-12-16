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

import com.pool.pool.model.Car;

public class RegisterCarFragment extends Fragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;
    View cont;
    public RegisterCarFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        cont = inflater.inflate(R.layout.fragment_register_car, container, false);
        Button b = (Button) cont.findViewById(R.id.rca_save);
        b.setOnClickListener(this);
        final EditText email_e = (EditText) cont.findViewById(R.id.rca_car);
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
        EditText car_e = (EditText) cont.findViewById(R.id.rca_car);
        String car_s = car_e.getText().toString();
        EditText seats_e = (EditText) cont.findViewById(R.id.rca_seats);
        int seats = Integer.parseInt(seats_e.getText().toString());
        mListener.carSaved(car_s, seats);
    }



    public interface OnFragmentInteractionListener {
        void carSaved(String car_S, int seats);
    }
}
