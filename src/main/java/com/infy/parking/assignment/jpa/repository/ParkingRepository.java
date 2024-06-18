package com.infy.parking.assignment.jpa.repository;

import com.infy.parking.assignment.jpa.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParkingRepository extends JpaRepository<Vehicle,Integer> {
    <Optional> Vehicle findByVehicleNumberAndStatusIsNull(String VehicleId);

}
