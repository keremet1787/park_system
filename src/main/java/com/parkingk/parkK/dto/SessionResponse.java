package com.parkingk.parkK.dto;

import com.parkingk.parkK.model.ParkingSession;
import com.parkingk.parkK.model.SessionStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record SessionResponse(
        Long id,
        Long vehicleId,
        Long spotId,
        LocalDateTime entryTime,
        LocalDateTime exitTime,
        BigDecimal cost,
        SessionStatus status
) {
    public static SessionResponse from(ParkingSession s) {
        return new SessionResponse(
                s.getId(),
                s.getVehicle().getId(),
                s.getSpot().getId(),
                s.getEntryTime(),
                s.getExitTime(),
                s.getCost(),
                s.getStatus()
        );
    }
}