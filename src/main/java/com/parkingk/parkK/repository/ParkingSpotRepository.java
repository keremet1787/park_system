package com.parkingk.parkK.repository;

import com.parkingk.parkK.model.ParkingSpot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, Long> {

    boolean existsByNumber(String number);

    List<ParkingSpot> findByOccupiedFalse();
}