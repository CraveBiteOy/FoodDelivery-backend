package com.fooddelivery.backend.Service;

import java.util.List;

import com.fooddelivery.backend.Models.Courier;
import com.fooddelivery.backend.Models.Enums.CourierStatus;
import com.fooddelivery.backend.Models.Enums.NavigationMode;

public interface CourierService {
    Courier getById(Long id);
    Courier getByAuthenticatedUser();
    Courier updateStatusForAuthCourier(CourierStatus status);
    Courier updateLocationforAuthCourier(Double latitude, Double longitude);
    List<Courier> getOnlineAndAvailableCouriers();
    List<Courier> getOnlineAndAvailableCouriersFromMode(NavigationMode mode);
    Courier updateAvailableForAuthCourier(boolean available);
    Courier updateAvailableAndLocationForAuthCourier(boolean available, Double latitude, Double longitude);
    Courier updateNavigationModeForCourier(NavigationMode mode);
    Boolean checkNewCourier();
}
