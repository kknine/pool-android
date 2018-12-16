package com.pool.pool;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.android.gms.maps.model.LatLng;
import com.pool.pool.model.User;

public class Auth {

    public static User getUser(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        return new User(preferences.getInt("id",1),preferences.getString("name",""),preferences.getString("email",""),new LatLng(preferences.getFloat("lat",0f),preferences.getFloat("lng",0f)));
    }
    public static void saveUser(Context context, User user) {
        SharedPreferences preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("name",user.getName());
        editor.putString("email",user.getEmail());
        editor.putInt("id",user.getId());
        editor.putFloat("lat",(float)user.getLocation().latitude);
        editor.putFloat("lng", (float)user.getLocation().longitude);
        editor.apply();
    }

}
