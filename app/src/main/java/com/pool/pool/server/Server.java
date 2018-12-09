package com.pool.pool.server;

import android.content.Context;
import android.telecom.Call;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;
import com.pool.pool.R;
import com.pool.pool.model.Car;
import com.pool.pool.model.Event;
import com.pool.pool.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Server {

    private Context context;
    private RequestQueue mRequestQueue;
    private boolean mBusy = false;

    public Server(Context context) {
        this.context = context;
        mRequestQueue = Volley.newRequestQueue(context);
    }

    public void singIn(String email, String pass, final Utils.Callback<User,String> callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                callback.onSuccess(new User(1,"Chris","chris@example.com",new LatLng(21.0,0.0)));

            }
        }).start();

    }

    public void registerNewUser(String email, String name, String pass, LatLng location, Car car, Utils.Callback<Boolean,String> callback) {
        callback.onSuccess(true);
    }

    public void getAvailableEvents(final Utils.Callback<ArrayList<Event>,String> callback) {
        //TODO: get actual id from preferences
        int userId = 1;
        ArrayList<Event> events = new ArrayList<>();
        events.add(new Event(1,"Birthday 1",new LatLng(22.0,0.0),"2018/12/13 08:00",1));
        events.add(new Event(2,"Birthday 2",new LatLng(21.0,0.0),"2018/12/14 10:00",2));
        callback.onSuccess(events);
    }
    public void getFriends(final Utils.Callback<ArrayList<User>,String> callback) {
        ArrayList<User> friends = new ArrayList<>();
        friends.add(new User(2,"Tom","tom@example.com",new LatLng(21.5,0.0)));
        friends.add(new User(3,"Peter","peter@example.com",new LatLng(21.3,0.1)));
        callback.onSuccess(friends);
    }

    public void getLatLngForLocation(String location,final Utils.Callback<LatLng,String> callback) {
        if(Utils.isReadyForQuery(context)) {
            String locationString = "location="+location;
            String url = Constants.MAPQUEST_GEOCODING_API + context.getString(R.string.mapquest_api_key) + "&" + locationString;
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            try {
                            JSONArray resultsJSON = jsonObject.getJSONArray("results");
                            JSONArray locationsJSON = resultsJSON.getJSONObject(0).getJSONArray("locations");
                            JSONObject latLngJSON = locationsJSON.getJSONObject(0).getJSONObject("latLng");
                            double lat = latLngJSON.getDouble("lat");
                            double lng = latLngJSON.getDouble("lng");
                            LatLng position = new LatLng(lat,lng);
                            callback.onSuccess(position);
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }, new Utils.ServerResponse<LatLng>().simpleError(callback)) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }
            };
            mRequestQueue.add(jsonObjectRequest);
        } else {
            callback.onFail(Constants.CONNECTION_ERROR);
        }
    }

    public void createNewEvent(Event event, Utils.Callback<Boolean,String> callback) {
        callback.onSuccess(true);
    }

    public void getInvitedGuests(int eventId, Utils.Callback<ArrayList<User>,String> callback) {
        ArrayList<User> friends = new ArrayList<>();
        friends.add(new User(2,"Tom","tom@example.com",new LatLng(21.5,0.0)));
        callback.onSuccess(friends);
    }
    public void getUninvitedFriends(int eventId, Utils.Callback<ArrayList<User>,String> callback) {
        ArrayList<User> friends = new ArrayList<>();
        friends.add(new User(3,"Peter","peter@example.com",new LatLng(21.3,0.1)));
        callback.onSuccess(friends);
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
