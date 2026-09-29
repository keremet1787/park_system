package com.parkingk.parkK.service;

import com.parkingk.parkK.dto.SpotRequest;
import com.parkingk.parkK.dto.SpotResponse;
import com.parkingk.parkK.exception.ConflictException;
import com.parkingk.parkK.exception.NotFoundException;
import com.parkingk.parkK.model.ParkingSpot;
import com.parkingk.parkK.repository.ParkingSessionRepository;
import com.parkingk.parkK.repository.ParkingSpotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParkingSpotService {

    private final ParkingSpotRepository repository;
    private final ParkingSessionRepository sessionRepository;

    public SpotResponse create(SpotRequest request) {
        if (repository.existsByNumber(request.number())) {
            throw new ConflictException("Место с таким номером уже существует");
        }
        ParkingSpot spot = new ParkingSpot();
        spot.setNumber(request.number());
        spot.setType(request.type());
        return SpotResponse.from(repository.save(spot));
    }

    public List<SpotResponse> findAll() {
        return repository.findAll().stream().map(SpotResponse::from).toList();
    }

    public List<SpotResponse> findFree() {
        return repository.findByOccupiedFalse().stream().map(SpotResponse::from).toList();
    }

    public SpotResponse findById(Long id) {
        return SpotResponse.from(getOrThrow(id));
    }

    public SpotResponse update(Long id, SpotRequest request) {
        ParkingSpot spot = getOrThrow(id);
        boolean numberChanged = !spot.getNumber().equals(request.number());
        if (numberChanged && repository.existsByNumber(request.number())) {
            throw new ConflictException("Этот номер места уже занят");
        }
        spot.setNumber(request.number());
        spot.setType(request.type());
        return SpotResponse.from(repository.save(spot));
    }

    public void delete(Long id) {
        ParkingSpot spot = getOrThrow(id);
        if (spot.isOccupied() || sessionRepository.existsBySpotId(id)) {
            throw new ConflictException("Нельзя удалить место: оно занято или есть история сессий");
        }
        repository.delete(spot);
    }

    private ParkingSpot getOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Парковочное место не найдено: " + id));
    }
}