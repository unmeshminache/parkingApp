package com.infy.parking.assignment.jpa.service;

import com.infy.parking.assignment.jpa.entity.StreetName;
import com.infy.parking.assignment.jpa.entity.Vehicle;
import com.infy.parking.assignment.jpa.exception.VehicleRegistartionNotFoundException;
import com.infy.parking.assignment.jpa.repository.ParkingRepository;
import com.infy.parking.assignment.jpa.repository.StreetNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
public class ParkingService {
    private static final String COMPLETED = "Completed";
    private static final String SUNDAY = "sunday";
    private static final String ACTIVE = "Active";
    @Autowired
    private ParkingRepository parkingRepository;
    @Autowired
    private StreetNameRepository streetNameRepository;

    public Vehicle getVehicle(String vehicleNumber) throws VehicleRegistartionNotFoundException {
        Vehicle vehicle = Optional.ofNullable(parkingRepository.findByVehicleNumberAndStatusIsActive(vehicleNumber)).orElseThrow(() -> new VehicleRegistartionNotFoundException("Vehicle not found with given vehicle number :" + vehicleNumber));
        return vehicle;
    }

    public Vehicle registerVehicle(Vehicle vehicleDetails) {

        Vehicle vehicle = parkingRepository.findByVehicleNumberAndStatusIsActive(vehicleDetails.getVehicleNumber());

        if (vehicle == null) {
            vehicleDetails.setStatus(ACTIVE);
            parkingRepository.save(vehicleDetails);
            return vehicleDetails;
        } else {
            throw new RuntimeException("Vehicle is already registered!!!");
        }
    }

    public double updateDetails(Vehicle vehicle) throws VehicleRegistartionNotFoundException {
        Vehicle existingDetails = getVehicle(vehicle.getVehicleNumber());
        existingDetails.setExitTime(vehicle.getExitTime());
        existingDetails.setExitDate(vehicle.getExitDate());
        existingDetails.setStatus(COMPLETED);
        parkingRepository.save(existingDetails);
        return calculateMinutes(existingDetails) * chargesAsPerStreet(existingDetails);
    }

    public double chargesAsPerStreet(Vehicle vehicle) {
        StreetName streetName = Optional.ofNullable(streetNameRepository.findByStreetName(vehicle.getStreetName())).orElseThrow(() -> new RuntimeException("Street not Found :" + vehicle.getStreetName()));
        return streetName.getPrice();
    }

    public long calculateMinutes(Vehicle vehicle) {
        LocalDateTime entryDateTime = LocalDateTime.of(vehicle.getEntryDate().getYear(), vehicle.getEntryDate().getMonth(), vehicle.getEntryDate().getDayOfMonth(), vehicle.getEntryTime().getHour(), vehicle.getEntryTime().getMinute(), vehicle.getEntryTime().getSecond());
        LocalDateTime exitDateTime = LocalDateTime.of(vehicle.getExitDate().getYear(), vehicle.getExitDate().getMonth(), vehicle.getExitDate().getDayOfMonth(), vehicle.getExitTime().getHour(), vehicle.getExitTime().getMinute(), vehicle.getExitTime().getSecond());
        if (entryDateTime.isAfter(exitDateTime)) {
            throw new RuntimeException("Invalid entry-exit time entered: Exit time is before the entry time");
        }
        Long totalMinutes = 0l;
        while (entryDateTime.isBefore(exitDateTime)) {
            entryDateTime = entryDateTime.plusMinutes(1);
            if ((entryDateTime.getHour() >= 8 && entryDateTime.getMinute() >= 0) && ((entryDateTime.getHour() < 21) && (entryDateTime.getMinute() >= 0)) && (String.valueOf(entryDateTime.getDayOfWeek()) != SUNDAY)) {
                ++totalMinutes;
            }
        }
        return totalMinutes;
    }

//    @ExceptionHandler(value = VehicleRegistartionNotFoundException.class)
//    @ResponseStatus(HttpStatus.CONFLICT)
//    public ErrorResponse vehicleRegistartionNotFoundException(VehicleRegistartionNotFoundException ex) {
//        return new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
//    }

}

