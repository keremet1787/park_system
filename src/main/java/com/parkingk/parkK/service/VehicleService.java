package com.parkingk.parkK.service;

import com.parkingk.parkK.dto.VehicleRequest;
import com.parkingk.parkK.dto.VehicleResponse;
import com.parkingk.parkK.exception.ConflictException;
import com.parkingk.parkK.exception.NotFoundException;
import com.parkingk.parkK.model.Vehicle;
import com.parkingk.parkK.repository.ParkingSessionRepository;
import com.parkingk.parkK.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository repository;
    private final ParkingSessionRepository sessionRepository;

    public VehicleResponse create(VehicleRequest request) {
        if (repository.existsByLicensePlate(request.licensePlate())) {
            throw new ConflictException("Автомобиль с таким номером уже существует");
        }
        Vehicle vehicle = new Vehicle();
        apply(vehicle, request);
        return VehicleResponse.from(repository.save(vehicle));
    }

    public List<VehicleResponse> findAll() {
        return repository.findAll().stream().map(VehicleResponse::from).toList();
    }

    public VehicleResponse findById(Long id) {
        return VehicleResponse.from(getOrThrow(id));
    }

    public VehicleResponse update(Long id, VehicleRequest request) {
        Vehicle vehicle = getOrThrow(id);
        boolean plateChanged = !vehicle.getLicensePlate().equals(request.licensePlate());
        if (plateChanged && repository.existsByLicensePlate(request.licensePlate())) {
            throw new ConflictException("Этот номер уже занят другим автомобилем");
        }
        apply(vehicle, request);
        return VehicleResponse.from(repository.save(vehicle));
    }

    public void delete(Long id) {
        Vehicle vehicle = getOrThrow(id);
        if (sessionRepository.existsByVehicleId(id)) {
            throw new ConflictException("Нельзя удалить автомобиль: у него есть парковочные сессии");
        }
        repository.delete(vehicle);
    }

    private Vehicle getOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Автомобиль не найден: " + id));
    }

    private void apply(Vehicle vehicle, VehicleRequest request) {
        vehicle.setLicensePlate(request.licensePlate());
        vehicle.setType(request.type());
        vehicle.setOwnerName(request.ownerName());
    }
}