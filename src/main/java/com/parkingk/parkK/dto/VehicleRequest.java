package com.parkingk.parkK.dto;

import com.parkingk.parkK.model.VehicleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VehicleRequest(
        @NotBlank(message = "Номер автомобиля обязателен")
        String licensePlate,

        @NotNull(message = "Тип автомобиля обязателен")
        VehicleType type,

        String ownerName
) {
}