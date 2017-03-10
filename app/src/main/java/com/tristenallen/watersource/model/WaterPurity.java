package com.tristenallen.watersource.model;

/**
 * Created by tristen on 3/10/17.
 *
 * Holds valid purity levels of water sources.
 */
public enum WaterPurity {
    SAFE("Safe"),
    TREATABLE("Treatable"),
    UNSAFE("Unsafe");

    String type;

    WaterPurity(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
