package com.fooddelivery.backend.Models.Enums;

public enum CourierStatus {
    ONLINE("ONLINE"),
    OFFLINE("OFFLINE");
    
    private String name;

    CourierStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
