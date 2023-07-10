package com.fooddelivery.backend.Service;

import java.util.List;

import com.fooddelivery.backend.Models.Courier;
import com.fooddelivery.backend.Models.Enums.CourierStatus;

public interface CourierService {
    Courier getById(Long id);
    Courier getByAuthenticatedUser();
    Courier updateStatusForAuthCourier(CourierStatus status);
    Courier updateLocationforAuthCourier(Double latitude, Double longitude);
    List<Courier> getOnlineAndAvailableCouriers();
    Courier updateAvailableForAuthCourier(boolean available);
    Courier updateAvailableAndLocationForAuthCourier(boolean available, Double latitude, Double longitude);
}
