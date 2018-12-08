package com.pool.pool;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.pool.pool.model.Car;
import com.pool.pool.server.Server;
import com.pool.pool.server.Utils;

public class RegisterActivity extends AppCompatActivity implements
        RegisterEmailFragment.OnFragmentInteractionListener,
        RegisterPassFragment.OnFragmentInteractionListener,
        RegisterLocationFragment.OnFragmentInteractionListener,
        RegisterCarFragment.OnFragmentInteractionListener {

    String email,pass,name;
    Car car;
    LatLng location;
    View RegisterForm;
    View RegisterProgress;
    private Server mServer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mServer = new Server(this);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.reg_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayShowTitleEnabled(false);
        ab.setDisplayHomeAsUpEnabled(true);
        if (findViewById(R.id.reg_fragment) != null) {
            if (savedInstanceState != null) {
                return;
            }
            RegisterEmailFragment firstFragment = new RegisterEmailFragment();
            firstFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.reg_fragment, firstFragment).commit();
        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void emailSaved(String em, String name) {
        email = em;
        System.out.println(email);
        RegisterPassFragment newFragment = new RegisterPassFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.reg_fragment, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void passwordSaved(String value) {
        pass = value;
        RegisterLocationFragment newFragment = new RegisterLocationFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.reg_fragment, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void locationSaved(String value) {
        //TODO: get the actual value
        location = new LatLng(21.0,0.0);
        RegisterCarFragment newFragment = new RegisterCarFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.reg_fragment, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    @Override
    public void carSaved(String value) {
        car = new Car(value);
        RegisterForm = findViewById(R.id.reg_form);
        RegisterProgress = findViewById(R.id.reg_progress);
        showProgress(true);
        View v = findViewById(R.id.rca_car);
        hideKeyboardFrom(getApplicationContext(),v);
        showProgress(true);
        mServer.registerNewUser(email, name, pass, location, car, new Utils.Callback<Boolean, String>() {
            @Override
            public void onSuccess(Boolean obj) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setTitle("You are signed up!");
                builder.setMessage("Your account was created, you can log in now");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(0, 0);
                    }
                });
                builder.show();
            }

            @Override
            public void onFail(String obj) {
                Toast.makeText(RegisterActivity.this,"Could not register new user",Toast.LENGTH_SHORT).show();

            }
        });
    }

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            RegisterForm.setVisibility(show ? View.GONE : View.VISIBLE);
            RegisterForm.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    RegisterForm.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            RegisterProgress.setVisibility(show ? View.VISIBLE : View.GONE);
            RegisterProgress.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    RegisterProgress.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            RegisterProgress.setVisibility(show ? View.VISIBLE : View.GONE);
            RegisterForm.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}
