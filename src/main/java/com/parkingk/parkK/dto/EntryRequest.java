package com.parkingk.parkK.dto;

import jakarta.validation.constraints.NotNull;

public record EntryRequest(
        @NotNull(message = "vehicleId обязателен")
        Long vehicleId,

        @NotNull(message = "spotId обязателен")
        Long spotId
) {
}