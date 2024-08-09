package com.infy.parking.assignment.jpa.repository;

import com.infy.parking.assignment.jpa.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;

public interface ParkingRepository extends JpaRepository<Vehicle,Integer> {
    @Query("SELECT p from Vehicle p where p.vehicleNumber = :vehicleNumber AND p.status = 'Active'")
    Vehicle findByVehicleNumberAndStatusIsActive(@Param("vehicleNumber") String vehicleNumber);

    @Query("SELECT v from Vehicle v where v.vehicleNumber = :vehicleNumber AND v.entryDate <= :dateOfObservation AND v.entryTime <= :timeOfObservation AND v.exitDate >= :dateOfObservation AND v.exitTime >= :timeOfObservation")
    Vehicle findByVehicleNumberDateAndTimeOfRegistration(@Param("vehicleNumber") String vehicleNumber, @Param("dateOfObservation") LocalDate dateOfObservation, @Param("timeOfObservation") LocalTime timeOfObservation);

}
