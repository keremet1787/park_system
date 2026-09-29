package com.parkingk.parkK.model;

import java.math.BigDecimal;

public enum VehicleType {
    CAR(100),
    MOTORCYCLE(50),
    TRUCK(150);

    private final BigDecimal hourlyRate;

    VehicleType(int rate) {
        this.hourlyRate = BigDecimal.valueOf(rate);
    }

    public BigDecimal getHourlyRate() {
        return hourlyRate;
    }
}