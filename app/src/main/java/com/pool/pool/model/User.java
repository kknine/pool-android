package com.pool.pool.model;

import com.google.android.gms.maps.model.LatLng;

public class User {

    private final String name;
    private final String email;
    private final LatLng location;

    public User(String name, String email, LatLng location) {
        this.name = name;
        this.email = email;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public LatLng getLocation() {
        return location;
    }
}
