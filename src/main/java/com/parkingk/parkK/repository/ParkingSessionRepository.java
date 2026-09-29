package com.parkingk.parkK.repository;

import com.parkingk.parkK.model.ParkingSession;
import com.parkingk.parkK.model.SessionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingSessionRepository extends JpaRepository<ParkingSession, Long> {

    boolean existsByVehicleIdAndStatus(Long vehicleId, SessionStatus status);

    boolean existsBySpotId(Long spotId);

    boolean existsByVehicleId(Long vehicleId);
}