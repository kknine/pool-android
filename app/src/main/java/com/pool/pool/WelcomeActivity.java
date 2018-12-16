package com.pool.pool;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }
    public void logIn(View v) {
        Intent i = new Intent(this,LoginActivity.class);
        startActivity(i);
    }
    public void signUp(View v) {
        Intent i = new Intent(this,RegisterActivity.class);
        startActivity(i);
    }
}
