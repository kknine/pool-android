package com.pool.pool.model;

public class Car {
    private final String name;
    private final int seats;

    public Car(String name, int seats) {
        this.name = name;
        this.seats = seats;
    }

    public String getName() {
        return name;
    }

    public int getSeats() {
        return seats;
    }
}
