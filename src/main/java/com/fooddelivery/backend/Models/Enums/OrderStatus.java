package com.fooddelivery.backend.Models.Enums;

public enum OrderStatus {
    NEW("NEW"),
    COOKING("COOKING"),
    READY_FOR_PICKUP("READY_FOR_PICKUP"),
    PICKED_UP("PICKED_UP"),
    COMPLETED("COMPLETED");
    

    private String name;

    OrderStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
