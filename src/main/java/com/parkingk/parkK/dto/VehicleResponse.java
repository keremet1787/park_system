package com.parkingk.parkK.dto;

import com.parkingk.parkK.model.Vehicle;
import com.parkingk.parkK.model.VehicleType;

public record VehicleResponse(
        Long id,
        String licensePlate,
        VehicleType type,
        String ownerName
) {
    public static VehicleResponse from(Vehicle v) {
        return new VehicleResponse(v.getId(), v.getLicensePlate(), v.getType(), v.getOwnerName());
    }
}