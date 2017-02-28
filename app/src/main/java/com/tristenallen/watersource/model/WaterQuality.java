package com.tristenallen.watersource.model;

/**
 * Created by Tristen on 2/25/2017.
 *
 * Represents the different qualities of water.
 */
public enum WaterQuality {
    WASTE("Waste"), MUDDY("Treatable, Muddy"), CLEAR("Treatable, Clear"),
    POTABLE("Potable"), UNKNOWN("Unknown");

    String name;

    WaterQuality(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
