package com.infy.parking.assignment.jpa.controller;

import com.infy.parking.assignment.jpa.entity.Vehicle;
import com.infy.parking.assignment.jpa.exception.VehicleRegistartionNotFoundException;
import com.infy.parking.assignment.jpa.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.LocalTime;

@RestController
@RequestMapping("/")
public class ParkingController {

    private ParkingService parkingService;

    @Autowired
    public ParkingController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @GetMapping
    @RequestMapping("/vehicles/{vehicleNumber}")
    public ResponseEntity<Object> getVehicle(@PathVariable("vehicleNumber") String vehicleNumber) throws VehicleRegistartionNotFoundException {
        Vehicle vehicle = parkingService.getVehicle(vehicleNumber);
        return new ResponseEntity<>(vehicle, HttpStatus.OK);
    }

    @PostMapping
    @RequestMapping("/register/")
    public ResponseEntity<Object> registerVehicle(@RequestBody Vehicle details) {
        if (details.getEntryDate() == null && details.getEntryTime() == null) {
            details.setEntryDate(LocalDate.now());
            details.setEntryTime(LocalTime.now());
        }
        Vehicle vehicleDetails = parkingService.registerVehicle(details);
        String message = "Your parking Session has begun with vehicle number : " + details.getVehicleNumber();
        ResponseEntity<Object> responseEntity = new ResponseEntity<>(message, HttpStatus.OK);
        return responseEntity;
    }

    @PutMapping
    @RequestMapping("/deregister/")
    public ResponseEntity<Object> deregisterVehicle(@RequestBody Vehicle vehicle) throws VehicleRegistartionNotFoundException {
        if (vehicle.getExitDate() == null && vehicle.getExitTime() == null) {
            vehicle.setExitDate(LocalDate.now());
            vehicle.setExitTime(LocalTime.now());
        }
        String message = "Total parking charges for your vehicle is : " + parkingService.updateDetails(vehicle) + " Cents";
        ResponseEntity<Object> responseEntity = new ResponseEntity<>(message, HttpStatus.OK);
        return responseEntity;
    }

}
