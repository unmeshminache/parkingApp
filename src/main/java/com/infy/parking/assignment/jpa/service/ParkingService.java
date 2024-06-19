package com.infy.parking.assignment.jpa.service;

import com.infy.parking.assignment.jpa.entity.StreetName;
import com.infy.parking.assignment.jpa.entity.Vehicle;
import com.infy.parking.assignment.jpa.exception.VehicleRegistartionNotFoundException;
import com.infy.parking.assignment.jpa.repository.ParkingRepository;
import com.infy.parking.assignment.jpa.repository.StreetNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class ParkingService {
    private static final String COMPLETED = "Completed";
    private static final String SUNDAY = "sunday";
    private static final String SATURDAY = "saturday";
    private static final String MONDAY = "monday";
    @Autowired
    private ParkingRepository parkingRepository;
    @Autowired
    private StreetNameRepository streetNameRepository;

    public Vehicle getVehicle(String vehicleNumber) throws VehicleRegistartionNotFoundException {
//        Optional<Vehicle> vehicleDetails = Optional.ofNullable(parkingRepository.findByVehicleNumberAndStatusIsNull(vehicleNumber));
//        if (vehicleDetails.isPresent()) {
//            return vehicleDetails.get();
//        } else {
//            throw new VehicleRegistartionNotFoundException("Vehicle not found with given vehicle number :" + vehicleNumber);
//        }
        Vehicle vehicle=Optional.ofNullable(parkingRepository.findByVehicleNumberAndStatusIsNull(vehicleNumber)).orElseThrow(()->new VehicleRegistartionNotFoundException("Vehicle not found with given vehicle number :" + vehicleNumber));
        return vehicle;
    }

    public Vehicle registerVehicle(Vehicle vehicleDetails) {
        Vehicle vehicle = parkingRepository.findByVehicleNumberAndStatusIsNull(vehicleDetails.getVehicleNumber());
        if (vehicle == null) {
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

        return calculateCharges(existingDetails);
    }

    public double calculateCharges(Vehicle vehicle) {
        double chargesAsPerStreet =  chargesAsPerStreet(vehicle);
        LocalDateTime entryTimestamp = LocalDateTime.of(vehicle.getEntryDate().getYear(), vehicle.getEntryDate().getMonth(), vehicle.getEntryDate().getDayOfMonth(), vehicle.getEntryTime().getHour(), vehicle.getEntryTime().getMinute(), vehicle.getEntryTime().getSecond());
        LocalDateTime exitTimestamp = LocalDateTime.of(vehicle.getExitDate().getYear(), vehicle.getExitDate().getMonth(), vehicle.getExitDate().getDayOfMonth(), vehicle.getExitTime().getHour(), vehicle.getExitTime().getMinute(), vehicle.getExitTime().getSecond());

        if (SUNDAY.equalsIgnoreCase(String.valueOf(vehicle.getEntryDate().getDayOfWeek()))) {
            vehicle.setEntryDate(vehicle.getEntryDate().plusDays(1));
            vehicle.setEntryTime(LocalTime.of(8, 0, 0));
        }
        if (SATURDAY.equalsIgnoreCase(String.valueOf(vehicle.getEntryDate().getDayOfWeek())) && (vehicle.getEntryTime().isAfter(LocalTime.of(21, 0, 0)))) {
            vehicle.setEntryDate(vehicle.getEntryDate().plusDays(2));
            vehicle.setEntryTime(LocalTime.of(8, 0, 0));
        }
        if (vehicle.getEntryTime().isBefore(LocalTime.of(8, 0, 0))) {
            vehicle.setEntryTime(LocalTime.of(8, 0, 0));
        }
        if (vehicle.getEntryTime().isAfter(LocalTime.of(21, 0, 0))) {
            vehicle.setEntryDate(vehicle.getEntryDate().plusDays(1));
            vehicle.setEntryTime(LocalTime.of(8, 0, 0));
        }

        if (MONDAY.equalsIgnoreCase(String.valueOf(vehicle.getEntryDate().getDayOfWeek())) && (vehicle.getEntryTime().isBefore(LocalTime.of(8, 0, 0)))) {
            vehicle.setEntryDate(vehicle.getExitDate().minusDays(2));
            vehicle.setEntryTime(LocalTime.of(21, 0, 0));
        }
        if (SUNDAY.equalsIgnoreCase(String.valueOf(vehicle.getExitDate().getDayOfWeek()))) {
            vehicle.setExitDate(vehicle.getExitDate().minusDays(1));
            vehicle.setExitTime(LocalTime.of(21, 0, 0));
        }
        if (vehicle.getExitTime().isAfter(LocalTime.of(21, 0, 0))) {
            vehicle.setExitTime(LocalTime.of(21, 0, 0));
        }
        if (vehicle.getEntryTime().isBefore(LocalTime.of(8, 0, 0))) {
            vehicle.setExitDate(vehicle.getExitDate().minusDays(1));
            vehicle.setEntryTime(LocalTime.of(21, 0, 0));
        }
        LocalDateTime entry = LocalDateTime.of(vehicle.getEntryDate().getYear(), vehicle.getEntryDate().getMonth(), vehicle.getEntryDate().getDayOfMonth(), vehicle.getEntryTime().getHour(), vehicle.getEntryTime().getMinute(), vehicle.getEntryTime().getSecond());
        LocalDateTime exit = LocalDateTime.of(vehicle.getExitDate().getYear(), vehicle.getExitDate().getMonth(), vehicle.getExitDate().getDayOfMonth(), vehicle.getExitTime().getHour(), vehicle.getExitTime().getMinute(), vehicle.getExitTime().getSecond());

        if (entry.isAfter(exit)) {
            return 0;
        }

        Long totalMinutes = Math.abs(Duration.between(exit, entry).toMinutes());

        LocalDate entrydate = LocalDate.of(entry.getYear(), entry.getMonth(), entry.getDayOfMonth());
        LocalDate exitdate = LocalDate.of(exit.getYear(), exit.getMonth(), exit.getDayOfMonth());
        long totalDays = Math.abs(DAYS.between(exitdate, entrydate));
        long sundayCount = 0;
        while (entrydate.isBefore(exitdate)) {
            if (SUNDAY.equalsIgnoreCase(String.valueOf(entrydate.getDayOfWeek()))) {
                sundayCount++;
            }
            entrydate = entrydate.plusDays(1);
        }
        long totalChargeableMinutes = totalMinutes - totalDays * 11 * 60 - sundayCount * 13 * 60;
        double totalFinalCharge = totalChargeableMinutes * chargesAsPerStreet;

        return totalFinalCharge;
    }

    public double chargesAsPerStreet(Vehicle vehicle) {
        StreetName streetName = Optional.ofNullable(streetNameRepository.findByStreetName(vehicle.getStreetName())).orElseThrow(()->new RuntimeException("Street not Found :"+vehicle.getStreetName()));
        return streetName.getPrice();
    }

}
