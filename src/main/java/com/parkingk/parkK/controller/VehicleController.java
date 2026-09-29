package com.parkingk.parkK.controller;

import com.parkingk.parkK.dto.VehicleRequest;
import com.parkingk.parkK.dto.VehicleResponse;
import com.parkingk.parkK.service.VehicleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
@RequiredArgsConstructor
@Tag(name = "Vehicles", description = "Управление автомобилями")
public class VehicleController {

    private final VehicleService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VehicleResponse create(@Valid @RequestBody VehicleRequest request) {
        return service.create(request);
    }

    @GetMapping
    public List<VehicleResponse> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public VehicleResponse getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public VehicleResponse update(@PathVariable Long id, @Valid @RequestBody VehicleRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}