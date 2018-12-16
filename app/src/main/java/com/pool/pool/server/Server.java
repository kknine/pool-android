package com.pool.pool.server;

import android.content.Context;
import android.telecom.Call;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.pool.pool.Auth;
import com.pool.pool.R;
import com.pool.pool.model.Car;
import com.pool.pool.model.Event;
import com.pool.pool.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class Server {

    private Context context;
    private RequestQueue mRequestQueue;
    private boolean mBusy = false;

    public Server(Context context) {
        this.context = context;
        mRequestQueue = Volley.newRequestQueue(context);
    }

    public void singIn(String email, String pass, final Utils.Callback<User,String> callback) {
//        if(Utils.isReadyForQuery(context)) {
//            String emailString = "email="+email;
//            String passwordString = "password="+pass;
//            String url = Constants.API_URL + "/users/login" + "?" + emailString + "&" + passwordString;
//            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
//                    new Response.Listener<JSONObject>() {
//                        @Override
//                        public void onResponse(JSONObject jsonObject) {
//                            try {
//                                int id = jsonObject.getInt("userId");
//                                String name = jsonObject.getString("name");
//                                String email = jsonObject.getString("email");
//                                double lat = jsonObject.getDouble("lat");
//                                double lng = jsonObject.getDouble("lng");
//                                LatLng location = new LatLng(lat,lng);
//                                callback.onSuccess(new User(id,name,email,location));
//                            } catch (JSONException e1) {
//                                e1.printStackTrace();
//                            }
//                        }
//                    }, new Utils.ServerResponse<User>().simpleError(callback)) {
//                @Override
//                public String getBodyContentType() {
//                    return "application/json; charset=utf-8";
//                }
//            };
//            mRequestQueue.add(jsonObjectRequest);
//        } else {
//            callback.onFail(Constants.CONNECTION_ERROR);
//        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                callback.onSuccess(new User(1,"Harris Mirza","mirza.harris@gmail.com",new LatLng(51.761108,-0.22144)));

            }
        }).start();

    }

    public void registerNewUser(String email, String name, String pass, LatLng location,final Utils.Callback<Integer,String> callback) {
        if(Utils.isReadyForQuery(context)) {
            String emailString = "email="+email;
            String passwordString = "password="+pass;
            String nameString = "name="+name;
            String latString = "lat="+Double.toString(location.latitude);
            String lngString = "lng="+Double.toString(location.longitude);
            String url = Constants.API_URL + "/users/create" + "?" +emailString + "&" + nameString + "&" + passwordString + "&" + latString + "&" + lngString;
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            try {
                                int id = jsonObject.getInt("id");
                                if(id>0)
                                    callback.onSuccess(id);
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }, new Utils.ServerResponse<Integer>().simpleError(callback)) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }
            };
            mRequestQueue.add(jsonObjectRequest);
        } else {
            callback.onFail(Constants.CONNECTION_ERROR);
        }
        //callback.onSuccess(true);
    }

    public void addCar(int userId, Car car, final Utils.Callback<Boolean,String> callback) {
        if(Utils.isReadyForQuery(context)) {
            String userIdString = "userId="+userId;
            String nicknameString = "nickname="+car.getName();
            String seatsString = "seats="+Integer.toString(car.getSeats());
            String url = Constants.API_URL + "/cars/create" + "?" + userIdString + "&" + nicknameString + "&" + seatsString;
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            try {
                                int id = jsonObject.getInt("id");
                                if(id>0)
                                    callback.onSuccess(true);
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }, new Utils.ServerResponse<Boolean>().simpleError(callback)) {
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

    public void getCar(int userId, final Utils.Callback<Car,String> callback) {
        if(Utils.isReadyForQuery(context)) {
            String userIdString = "userId="+userId;
            String url = Constants.API_URL + "/cars" + "?" + userIdString;
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray jsonArray) {
                            try {
                                JSONObject carJSON = jsonArray.getJSONObject(0);
                                Car car = new Car(carJSON.getString("nickname"),carJSON.getInt("seats"));
                                callback.onSuccess(car);
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }, new Utils.ServerResponse<Car>().simpleError(callback)) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }
            };
            mRequestQueue.add(jsonArrayRequest);
        } else {
            callback.onFail(Constants.CONNECTION_ERROR);
        }
    }

    public void getAvailableEvents(final Utils.Callback<ArrayList<Event>,String> callback) {
        //TODO: get actual id from preferences
        int userId = Auth.getUser(context).getId();
//        ArrayList<Event> events = new ArrayList<>();
//        events.add(new Event(1,"Birthday 1",new LatLng(22.0,0.0),"2018/12/13 08:00",1));
//        events.add(new Event(2,"Birthday 2",new LatLng(21.0,0.0),"2018/12/14 10:00",2));
//        callback.onSuccess(events);
        if(Utils.isReadyForQuery(context)) {
            String userString = "userId="+Integer.toString(userId);
            String url = Constants.API_URL + "/events" + "?" + userString;
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray jsonArray) {
                            try {
                                ArrayList<Event> events = new ArrayList<>();
                                for(int i = 0; i < jsonArray.length();i++) {
                                    JSONObject eventJSON = jsonArray.getJSONObject(i);
                                    int eventId = eventJSON.getInt("event_id");
                                    String name = eventJSON.getString("name");
                                    String datetime = eventJSON.getString("datetime");
                                    double lat = eventJSON.getDouble("lat");
                                    double lng = eventJSON.getDouble("lng");
                                    int ownerId = eventJSON.getInt("owner_id");
                                    events.add(new Event(eventId,name,new LatLng(lat,lng),datetime,ownerId));
                                }
                                callback.onSuccess(events);
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }, new Utils.ServerResponse<ArrayList<Event>>().simpleError(callback)) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }
            };
            mRequestQueue.add(jsonArrayRequest);
        } else {
            callback.onFail(Constants.CONNECTION_ERROR);
        }
    }
    public void getFriends(final Utils.Callback<ArrayList<User>,String> callback) {
//        ArrayList<User> friends = new ArrayList<>();
//        friends.add(new User(2,"Tom","tom@example.com",new LatLng(21.5,0.0)));
//        friends.add(new User(3,"Peter","peter@example.com",new LatLng(21.3,0.1)));
//        callback.onSuccess(friends);
        if(Utils.isReadyForQuery(context)) {
            String userString = "userId="+Integer.toString(Auth.getUser(context).getId());
            String url = Constants.API_URL + "/users/friends" + "?" + userString;
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray jsonArray) {
                            try {
                                ArrayList<User> friends = new ArrayList<>();
                                for(int i = 0; i < jsonArray.length();i++) {
                                    JSONObject friendJSON = jsonArray.getJSONObject(i);
                                    int userId = friendJSON.getInt("id");
                                    String name = friendJSON.getString("name");
                                    double lat = friendJSON.getDouble("lat");
                                    double lng = friendJSON.getDouble("lng");
                                    String email = friendJSON.getString("email");
                                    friends.add(new User(userId,name,email,new LatLng(lat,lng)));
                                }
                                callback.onSuccess(friends);
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }, new Utils.ServerResponse<ArrayList<User>>().simpleError(callback)) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }
            };
            mRequestQueue.add(jsonArrayRequest);
        } else {
            callback.onFail(Constants.CONNECTION_ERROR);
        }
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

    public void createNewEvent(Event event, final Utils.Callback<Integer,String> callback) {
        if(Utils.isReadyForQuery(context)) {
            String nameString = "eventName="+event.getName();
            String ownerString = "eventOwner="+Integer.toString(event.getOwnerId());
            String datetimeString = "datetime="+event.getDatetime();
            String latString = "lat="+Double.toString(event.getLocation().latitude);
            String lngString = "lng="+Double.toString(event.getLocation().longitude);
            String url = Constants.API_URL + "/events/create" + "?" + nameString + "&" + ownerString + "&" + datetimeString + "&" + latString + "&" + lngString;
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            try {
                                int id = jsonObject.getInt("id");
                                if(id>0)
                                    callback.onSuccess(id);
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }, new Utils.ServerResponse<Integer>().simpleError(callback)) {
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

    public void getInvitedGuests(int eventId, final Utils.Callback<ArrayList<User>,String> callback) {
        if(Utils.isReadyForQuery(context)) {
            String eventString = "eventId="+Integer.toString(eventId);
            String url = Constants.API_URL + "/events/invited" + "?" + eventString;
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray jsonArray) {
                            try {
                                ArrayList<User> friends = new ArrayList<>();
                                for(int i = 0; i < jsonArray.length();i++) {
                                    JSONObject friendJSON = jsonArray.getJSONObject(i);
                                    int userId = friendJSON.getInt("id");
                                    String name = friendJSON.getString("name");
                                    double lat = friendJSON.getDouble("lat");
                                    double lng = friendJSON.getDouble("lng");
                                    String email = friendJSON.getString("email");
                                    friends.add(new User(userId,name,email,new LatLng(lat,lng)));
                                }
                                callback.onSuccess(friends);
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }, new Utils.ServerResponse<ArrayList<User>>().simpleError(callback)) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }
            };
            mRequestQueue.add(jsonArrayRequest);
        } else {
            callback.onFail(Constants.CONNECTION_ERROR);
        }

//        ArrayList<User> friends = new ArrayList<>();
//        friends.add(new User(2,"Tom","tom@example.com",new LatLng(21.5,0.0)));
//        callback.onSuccess(friends);
    }

    public void inviteFriend(int eventId, int userId, Utils.Callback<String,String> callback) {
        if(Utils.isReadyForQuery(context)) {
            String eventString = "eventId="+Integer.toString(eventId);
            String userString = "friendId="+Integer.toString(userId);
            String url = Constants.API_URL + "/events/invite" + "?" + eventString + "&" + userString;
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                        }
                    }, new Utils.ServerResponse<String>().simpleError(callback)) {
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
    public void getPolylines(int eventId, final Utils.Callback<ArrayList<PolylineOptions>,String> callback) {
        if(Utils.isReadyForQuery(context)) {
            String eventIdString = "eventId="+eventId;
            String url = Constants.API_URL + "/events/travel" + "?" + eventIdString;
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            ArrayList<PolylineOptions> polies = new ArrayList<>();
                            try {
                               Iterator<String> keys = jsonObject.keys();
                               while(keys.hasNext()) {
                                   PolylineOptions poly = new PolylineOptions();
                                   JSONObject object  = jsonObject.getJSONArray(keys.next()).getJSONObject(0);
                                   JSONObject directions = object.getJSONObject("directions");
                                   JSONObject properties = directions.getJSONArray("properties").getJSONObject(0);
                                   JSONObject route = properties.getJSONObject("route");
                                   JSONArray parts = route.getJSONArray("parts");
                                   for(int i = 0; i < parts.length(); i++) {
                                       JSONArray coords = parts.getJSONObject(i).getJSONArray("coords");
                                       for(int j = 0; j < coords.length(); j++) {
                                           JSONObject latlng = coords.getJSONObject(j);
                                           double lat = latlng.getDouble("lat");
                                           double lng = latlng.getDouble("lng");
                                           poly.add(new LatLng(lat,lng));
                                       }
                                   }
                                   polies.add(poly);
                               }
                               callback.onSuccess(polies);
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }, new Utils.ServerResponse<ArrayList<PolylineOptions>>().simpleError(callback)) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }
            };
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(120000,
                    1,
                    1));
            mRequestQueue.add(jsonObjectRequest);
        } else {
            callback.onFail(Constants.CONNECTION_ERROR);
        }

    }

    public void getUninvitedFriends(int eventId, final Utils.Callback<ArrayList<User>,String> callback) {
        if(Utils.isReadyForQuery(context)) {
            String eventString = "eventId="+Integer.toString(eventId);
            String userString = "userId="+Integer.toString(Auth.getUser(context).getId());
            String url = Constants.API_URL + "/events/notinvited" + "?" + eventString + "&" + userString;
            System.out.println(url);
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray jsonArray) {
                            System.out.println("tutaj");
                            try {
                                ArrayList<User> friends = new ArrayList<>();
                                for(int i = 0; i < jsonArray.length();i++) {
                                    JSONObject friendJSON = jsonArray.getJSONObject(i);
                                    int userId = friendJSON.getInt("id");
                                    String name = friendJSON.getString("name");
                                    double lat = friendJSON.getDouble("lat");
                                    double lng = friendJSON.getDouble("lng");
                                    String email = friendJSON.getString("email");
                                    friends.add(new User(userId,name,email,new LatLng(lat,lng)));
                                }
                                callback.onSuccess(friends);
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }, new Utils.ServerResponse<ArrayList<User>>().simpleError(callback)) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }
            };
            mRequestQueue.add(jsonArrayRequest);
        } else {
            callback.onFail(Constants.CONNECTION_ERROR);
        }
//        ArrayList<User> friends = new ArrayList<>();
//        friends.add(new User(3,"Peter","peter@example.com",new LatLng(21.3,0.1)));
//        callback.onSuccess(friends);
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
