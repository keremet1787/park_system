package com.parkingk.parkK.controller;

import com.parkingk.parkK.dto.SpotRequest;
import com.parkingk.parkK.dto.SpotResponse;
import com.parkingk.parkK.service.ParkingSpotService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parking-spots")
@RequiredArgsConstructor
@Tag(name = "Parking Spots", description = "Управление парковочными местами")
public class ParkingSpotController {

    private final ParkingSpotService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SpotResponse create(@Valid @RequestBody SpotRequest request) {
        return service.create(request);
    }

    @GetMapping
    public List<SpotResponse> getAll() {
        return service.findAll();
    }

    @GetMapping("/free")
    public List<SpotResponse> getFree() {
        return service.findFree();
    }

    @GetMapping("/{id}")
    public SpotResponse getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public SpotResponse update(@PathVariable Long id, @Valid @RequestBody SpotRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}