package com.infy.parking.assignment.jpa.service;

import com.infy.parking.assignment.jpa.entity.Address;
import com.infy.parking.assignment.jpa.entity.ParkedVehicles;
import com.infy.parking.assignment.jpa.entity.Vehicle;
import com.infy.parking.assignment.jpa.repository.AddressRepository;
import com.infy.parking.assignment.jpa.repository.ParkedVehiclesRepository;
import com.infy.parking.assignment.jpa.repository.ParkingRepository;
import com.opencsv.CSVWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${fileNameForPenalty}")
    private String nameOfPenaltyFile;
    @Value("${baseUrlPath}")
    private String basePath;
    @Value("${penalty.notification.file.name}")
    private String penaltyNotificationFileName;
    static final String VEHICLE_NUMBER = "Vehicle Number";
    static final String DATE_OF_OBSERVATION = "Date Of Observation";
    static final String STREET_NAME = "Street Name";

    public void registerMonitoredVehicle(List<ParkedVehicles> parkedVehicles) {
        parkedVehicles.forEach(vehicle -> repository.save(vehicle));
    }

    public List<ParkedVehicles> checkUnregisteredVehicles() throws IOException {
        List<ParkedVehicles> listOfParkedVehicles = repository.findAll();
        List<ParkedVehicles> unregisteredVehicles = new ArrayList<>();
        CSVWriter write = new CSVWriter(new FileWriter(basePath + nameOfPenaltyFile));
        write.writeNext(new String[]{VEHICLE_NUMBER, DATE_OF_OBSERVATION, STREET_NAME});
        for (ParkedVehicles vehicle : listOfParkedVehicles) {
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
            StringBuffer penaltyLetter = new StringBuffer();
            penaltyLetter.append(basePath).append(penaltyNotificationFileName).append(vehicle.getVehicleNumber());
            CSVWriter writer = new CSVWriter(new FileWriter(penaltyLetter.toString()));
            writer.writeNext(new String[]{"Hi " + vehicle.getNameOfOwner() + ", This is to inform you that, your vehicle with number " + vehicle.getVehicleNumber() + " found illegally parked on the street names " + vehicle.getStreetname() + ". Resulting the you are facing the penalty of 100 Euros."});
            writer.flush();
        }

        return unregisteredVehicles;
    }
}
