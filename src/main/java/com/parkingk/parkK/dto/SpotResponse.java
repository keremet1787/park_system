package com.parkingk.parkK.dto;

import com.parkingk.parkK.model.ParkingSpot;
import com.parkingk.parkK.model.VehicleType;

public record SpotResponse(
        Long id,
        String number,
        VehicleType type,
        boolean occupied
) {
    public static SpotResponse from(ParkingSpot s) {
        return new SpotResponse(s.getId(), s.getNumber(), s.getType(), s.isOccupied());
    }
}