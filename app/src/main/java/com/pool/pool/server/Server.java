package com.pool.pool.server;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;
import com.pool.pool.model.Car;
import com.pool.pool.model.User;

public class Server {

    private Context context;
    private RequestQueue mRequestQueue;
    private boolean mBusy = false;

    public Server(Context context) {
        this.context = context;
        mRequestQueue = Volley.newRequestQueue(context);
    }

    public void singIn(String email, String pass, Utils.Callback<User,String> callback) {
        callback.onSuccess(new User("Chris","chris@example.com",new LatLng(21.0,0.0)));
    }

    public void registerNewUser(String email, String name, String pass, LatLng location, Car car, Utils.Callback<Boolean,String> callback) {
        callback.onSuccess(true);
    }
    public void busy() {
        mBusy = true;
    }
    public void finished() {
        mBusy = false;
    }
    public boolean isBusy() {
        return mBusy;
    }

}
