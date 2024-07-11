package com.infy.parking.assignment.jpa.repository;

import com.infy.parking.assignment.jpa.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ParkingRepository extends JpaRepository<Vehicle,Integer> {
    @Query("SELECT p from Vehicle p where p.vehicleNumber = :vehicleNumber AND p.status = 'Active'")
    Vehicle findByVehicleNumberAndStatusIsActive(@Param("vehicleNumber") String vehicleNumber);


}
