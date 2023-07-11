package com.fooddelivery.backend.Models.Enums;

public enum OrderStatus {
    NEW("NEW"),
    OWNER_REJECTED("OWNER_REJECTED"),
    COOKING("COOKING"),
    READY_FOR_PICKUP("READY_FOR_PICKUP"),
    COURIER_ACCEPTED("COURIER_ACCEPTED"),
    COURIER_REJECTED("COURIER_REJECTED"),
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
