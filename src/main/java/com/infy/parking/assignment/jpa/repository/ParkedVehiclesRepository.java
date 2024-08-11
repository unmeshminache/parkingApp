package com.infy.parking.assignment.jpa.repository;

import com.infy.parking.assignment.jpa.entity.ParkedVehicles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkedVehiclesRepository extends JpaRepository<ParkedVehicles,Integer> {


}
