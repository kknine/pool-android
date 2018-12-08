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

public class RegisterPassFragment extends Fragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;
    View cont;
    public RegisterPassFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        cont = inflater.inflate(R.layout.fragment_register_pass, container, false);
        Button b = (Button) cont.findViewById(R.id.rpa_save);
        b.setOnClickListener(this);
        final EditText email_e = (EditText) cont.findViewById(R.id.rpa_pass);
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
        EditText pass_1_e = (EditText) cont.findViewById(R.id.rpa_pass);
        EditText pass_2_e = (EditText) cont.findViewById(R.id.rpa_pass_2);
        String pass1 = pass_1_e.getText().toString();
        String pass2 = pass_2_e.getText().toString();
        if(validatePass(pass1,pass2)) {
            mListener.passwordSaved(pass1);
        }
    }

    private boolean validatePass(String pass1, String pass2) {
        // TODO: do validation
        EditText pass_e = (EditText) cont.findViewById(R.id.rpa_pass);
//        boolean cancel = false;
//        if(TextUtils.isEmpty(email)) {
//            pass_e.setError(getString(R.string.error_field_required));
//            cancel = true;
//        } else if(!validAdress(email)) {
//            pass_e.setError(getString(R.string.error_invalid_email));
//            cancel = true;
//        }
//        if (cancel) {
//            pass_e.requestFocus();
//            return false;
//        } else {
//            return true;
//        }
        return true;
    }


    public interface OnFragmentInteractionListener {
        void passwordSaved(String pass);
    }
}
