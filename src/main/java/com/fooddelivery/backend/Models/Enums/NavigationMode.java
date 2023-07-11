package com.fooddelivery.backend.Models.Enums;

public enum NavigationMode {
    BICYCLE("BICYCLE"),
    CAR("CAR");
    
    private String name;

    NavigationMode(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
