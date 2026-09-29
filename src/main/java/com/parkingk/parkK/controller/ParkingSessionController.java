package com.parkingk.parkK.controller;

import com.parkingk.parkK.dto.EntryRequest;
import com.parkingk.parkK.dto.SessionResponse;
import com.parkingk.parkK.service.ParkingSessionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sessions")
@RequiredArgsConstructor
@Tag(name = "Parking Sessions", description = "Въезд и выезд автомобилей")
public class ParkingSessionController {

    private final ParkingSessionService service;

    @PostMapping("/entry")
    @ResponseStatus(HttpStatus.CREATED)
    public SessionResponse enter(@Valid @RequestBody EntryRequest request) {
        return service.enter(request);
    }

    @PostMapping("/{id}/exit")
    public SessionResponse exit(@PathVariable Long id) {
        return service.exit(id);
    }

    @GetMapping
    public List<SessionResponse> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public SessionResponse getById(@PathVariable Long id) {
        return service.findById(id);
    }
}