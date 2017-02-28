package com.tristenallen.watersource.model;

/**
 * Created by Tristen on 2/25/2017.
 *
 * Represents the different sources of water.
 */
public enum WaterType {
    BOTTLE("Bottled"), WELL("Well"), STREAM("Stream"),
    LAKE("Lake"), SPRING("Spring"), OTHER("Other"), UNKNOWN("Unknown");

    String name;

    WaterType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
