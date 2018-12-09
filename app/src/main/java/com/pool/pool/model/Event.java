package com.pool.pool.model;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

public class Event {
    private final int id;
    private final String name;
    private final LatLng location;
    private final String datetime;
    private final int ownerId;

    public Event(int id, String name, LatLng location, String datetime, int ownerId) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.datetime = datetime;
        this.ownerId = ownerId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LatLng getLocation() {
        return location;
    }

    public String getDatetime() {
        return datetime;
    }
    public int getOwnerId() {
        return ownerId;
    }
}
