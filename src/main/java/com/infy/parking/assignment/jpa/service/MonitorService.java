package com.infy.parking.assignment.jpa.service;

import com.infy.parking.assignment.jpa.entity.Address;
import com.infy.parking.assignment.jpa.entity.ParkedVehicles;
import com.infy.parking.assignment.jpa.entity.Vehicle;
import com.infy.parking.assignment.jpa.repository.AddressRepository;
import com.infy.parking.assignment.jpa.repository.ParkedVehiclesRepository;
import com.infy.parking.assignment.jpa.repository.ParkingRepository;
import com.opencsv.CSVWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MonitorService {
    @Autowired
    private ParkedVehiclesRepository repository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private ParkingRepository parkingRepository;

    public void registerMonitoredVehicle(List<ParkedVehicles> parkedVehicles) {
        parkedVehicles.forEach(vehicle -> repository.save(vehicle));
    }

    public List<ParkedVehicles> checkUnregisteredVehicles() throws IOException {
        List<ParkedVehicles> listOfParkedVehicles = repository.findAll();
        List<ParkedVehicles> unregisteredVehicles = new ArrayList<>();
        CSVWriter write = new CSVWriter(new FileWriter("data//unregistered-vehicle-data.csv"));
        write.writeNext(new String[]{"Vehicle Number", "Date Of Observation", "Street Name"});
        for (ParkedVehicles vehicle : listOfParkedVehicles) {
//            Optional<Vehicle> vehicleFromRegistration= Optional.of(parkingRepository.findByVehicleNumberDateAndTimeOfRegistration(vehicle.getVehicleNumber(),vehicle.getDateOfObservation(),vehicle.getTimeOfObservation()));
            Vehicle vehicleFromRegistration = parkingRepository.findByVehicleNumberAndStatusIsActive(vehicle.getVehicleNumber());
            if (vehicleFromRegistration == null) {
                unregisteredVehicles.add(vehicle);
            }
        }
        for (ParkedVehicles vehicle : unregisteredVehicles) {
            Address address = addressRepository.findByVehicleNumber(vehicle.getVehicleNumber());
            vehicle.setAddress(address.getAddress());
            vehicle.setNameOfOwner(address.getName());

            write.writeNext(new String[]{vehicle.getVehicleNumber(), String.valueOf(vehicle.getDateOfObservation()), vehicle.getStreetname()});
            write.flush();
        }

        return unregisteredVehicles;
    }
}
