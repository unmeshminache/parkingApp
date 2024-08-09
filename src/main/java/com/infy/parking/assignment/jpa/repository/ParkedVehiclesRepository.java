package com.infy.parking.assignment.jpa.repository;

import com.infy.parking.assignment.jpa.entity.ParkedVehicles;
import com.infy.parking.assignment.jpa.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkedVehiclesRepository extends JpaRepository<ParkedVehicles,Integer> {

//    @Query("SELECT p from ParkedVehicles p where p.address.vehicleNumber = :vehicleNumber")
//    Vehicle findByVehicleNumber(@Param("vehicleNumber") String vehicleNumber);
//

}
