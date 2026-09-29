package com.parkingk.parkK.service;

import com.parkingk.parkK.dto.EntryRequest;
import com.parkingk.parkK.dto.SessionResponse;
import com.parkingk.parkK.exception.BadRequestException;
import com.parkingk.parkK.exception.ConflictException;
import com.parkingk.parkK.exception.NotFoundException;
import com.parkingk.parkK.model.*;
import com.parkingk.parkK.repository.ParkingSessionRepository;
import com.parkingk.parkK.repository.ParkingSpotRepository;
import com.parkingk.parkK.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ParkingSessionService {

    private final ParkingSessionRepository sessionRepository;
    private final VehicleRepository vehicleRepository;
    private final ParkingSpotRepository spotRepository;

    // Въезд
    @Transactional
    public SessionResponse enter(EntryRequest request) {
        Vehicle vehicle = vehicleRepository.findById(request.vehicleId())
                .orElseThrow(() -> new NotFoundException("Автомобиль не найден: " + request.vehicleId()));
        ParkingSpot spot = spotRepository.findById(request.spotId())
                .orElseThrow(() -> new NotFoundException("Парковочное место не найдено: " + request.spotId()));

        if (vehicle.getType() != spot.getType()) {
            throw new BadRequestException("Тип автомобиля (" + vehicle.getType()
                    + ") не подходит для места типа " + spot.getType());
        }
        if (spot.isOccupied()) {
            throw new ConflictException("Парковочное место уже занято");
        }
        if (sessionRepository.existsByVehicleIdAndStatus(vehicle.getId(), SessionStatus.ACTIVE)) {
            throw new ConflictException("Этот автомобиль уже припаркован");
        }

        spot.setOccupied(true);

        ParkingSession session = new ParkingSession();
        session.setVehicle(vehicle);
        session.setSpot(spot);
        session.setEntryTime(LocalDateTime.now());
        session.setStatus(SessionStatus.ACTIVE);

        return SessionResponse.from(sessionRepository.save(session));
    }

    // Выезд
    @Transactional
    public SessionResponse exit(Long sessionId) {
        ParkingSession session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new NotFoundException("Сессия не найдена: " + sessionId));

        if (session.getStatus() != SessionStatus.ACTIVE) {
            throw new ConflictException("Сессия уже завершена");
        }

        LocalDateTime now = LocalDateTime.now();
        session.setExitTime(now);
        session.setCost(calculateCost(session.getVehicle().getType(), session.getEntryTime(), now));
        session.setStatus(SessionStatus.COMPLETED);
        session.getSpot().setOccupied(false);

        return SessionResponse.from(session);
    }

    public SessionResponse findById(Long id) {
        return SessionResponse.from(sessionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Сессия не найдена: " + id)));
    }

    public List<SessionResponse> findAll() {
        return sessionRepository.findAll().stream().map(SessionResponse::from).toList();
    }

    // Стоимость: округление вверх до полного часа, минимум 1 час
    BigDecimal calculateCost(VehicleType type, LocalDateTime from, LocalDateTime to) {
        long minutes = Duration.between(from, to).toMinutes();
        long hours = Math.max(1, (long) Math.ceil(minutes / 60.0));
        return type.getHourlyRate().multiply(BigDecimal.valueOf(hours));
    }
}