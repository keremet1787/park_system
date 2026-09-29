package com.parkingk.parkK.repository;

import com.parkingk.parkK.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    boolean existsByLicensePlate(String licensePlate);
}