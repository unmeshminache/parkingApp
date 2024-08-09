package com.infy.parking.assignment.jpa.repository;

import com.infy.parking.assignment.jpa.entity.Address;
import com.infy.parking.assignment.jpa.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address,Integer> {
    /*@Query("SELECT p from Address p where p.vehicles.vehicleNumber = :vehicleNumber")
    Vehicle findByVehicleNumber(@Param("vehicleNumber") String vehicleNumber);
*/
    @Query("SELECT p from Address p where p.vehicleNumber = :vehicleNumber")
    Address findByVehicleNumber(@Param("vehicleNumber") String vehicleNumber);


}
