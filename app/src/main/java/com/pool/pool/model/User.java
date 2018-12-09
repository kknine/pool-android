package com.pool.pool.model;

import com.google.android.gms.maps.model.LatLng;

public class User {

    private final int id;
    private final String name;
    private final String email;
    private final LatLng location;

    public User(int id, String name, String email, LatLng location) {
        this.id = id;
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

    public int getId() {
        return id;
    }
}
