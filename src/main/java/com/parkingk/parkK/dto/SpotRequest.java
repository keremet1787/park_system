package com.parkingk.parkK.dto;

import com.parkingk.parkK.model.VehicleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SpotRequest(
        @NotBlank(message = "Номер места обязателен")
        String number,

        @NotNull(message = "Тип места обязателен")
        VehicleType type
) {
}