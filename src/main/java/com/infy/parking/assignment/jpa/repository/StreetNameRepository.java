package com.infy.parking.assignment.jpa.repository;

import com.infy.parking.assignment.jpa.entity.StreetName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StreetNameRepository extends JpaRepository<StreetName,Integer> {
    public StreetName findByStreetName(String streetName);

}
