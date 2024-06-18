package com.infy.parking.assignment.jpa.service;

import com.infy.parking.assignment.jpa.entity.StreetName;
import com.infy.parking.assignment.jpa.entity.Vehicle;
import com.infy.parking.assignment.jpa.repository.ParkingRepository;
import com.infy.parking.assignment.jpa.repository.StreetNameRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;

@ExtendWith(MockitoExtension.class)
public class ParkingServiceTest {
    @InjectMocks
    private ParkingService service;
    @Mock
    private ParkingRepository repository;
    @Mock
    private StreetNameRepository streetNameRepository;

    private final static String JAVA = "Java";
    private final static String COMPLETED = "Completed";
    private final static String AZURE = "Azure";
    private final static String JAKARTA = "Jakarta";

    @Test
    public void testGetVehicle() {
        Vehicle expectedResult = createVehicle();
        Mockito.when(repository.findByVehicleNumberAndStatusIsNull(expectedResult.getVehicleNumber())).thenReturn(expectedResult);
        Vehicle actualResult = service.getVehicle(expectedResult.getVehicleNumber());
        Assertions.assertNotNull(actualResult);
        Assertions.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void testRegisterVehicle() {
        Vehicle vehicle = createVehicle();
        Vehicle expectedResult = createVehicle();
        Mockito.when(repository.save(vehicle)).thenReturn(null);
        Vehicle actualResult = service.registerVehicle(vehicle);
        Assertions.assertNotNull(actualResult);
        Assertions.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void testUpdateDetails() {
        Vehicle vehicleFromDAO = createVehicleForDao();
        Vehicle vehicle = createVehicleToUpdateDetails();
        StreetName streetName = createObjectForjava();
        Mockito.when(repository.findByVehicleNumberAndStatusIsNull(vehicleFromDAO.getVehicleNumber())).thenReturn(vehicleFromDAO);
        Mockito.when(streetNameRepository.findByStreetName(JAVA)).thenReturn(streetName);
        double actualResult = service.updateDetails(vehicle);
        Assertions.assertNotNull(actualResult);
        Assertions.assertEquals(actualResult, 900.0);

    }

    public Vehicle createVehicleToUpdateDetails() {
        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleNumber("A123");
        vehicle.setExitTime(LocalTime.of(13, 00, 00));
        vehicle.setExitDate(LocalDate.of(2024, 06, 14));
        return vehicle;
    }

    public Vehicle createVehicleForDao() {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(1);
        vehicle.setEntryTime(LocalTime.of(12, 0, 0));
        vehicle.setEntryDate(LocalDate.of(2024, 6, 14));
        vehicle.setVehicleNumber("A123");
        vehicle.setStreetName(JAVA);
        return vehicle;
    }

    @Test
    public void testCalculateCharges() {

        Vehicle vehicle = createVehicle();
        double expectedResult = 9900.0;
        StreetName streetName = createObjectForjava();
        Mockito.when(streetNameRepository.findByStreetName(JAVA)).thenReturn(streetName);
        double actualResult = service.calculateCharges(vehicle);
        Assertions.assertNotNull(actualResult);
        Assertions.assertEquals(actualResult, expectedResult);
    }

    public StreetName createObjectForjava() {
        StreetName streetName = new StreetName();
        streetName.setId(1);
        streetName.setStreetName(JAVA);
        streetName.setPrice(15);
        return streetName;
    }

    private Vehicle createVehicle() {

        Vehicle vehicle = new Vehicle();
        vehicle.setId(1);
        String vehicleId = "12345";
        vehicle.setVehicleNumber(vehicleId);
        vehicle.setEntryTime(LocalTime.of(12, 0, 0));
        vehicle.setEntryDate(LocalDate.of(2024, 06, 14));
        vehicle.setExitDate(LocalDate.of(2024, 06, 15));
        vehicle.setExitTime(LocalTime.of(10, 0, 0));
        vehicle.setStatus(COMPLETED);
        vehicle.setStreetName(JAVA);

        return vehicle;
    }


    @Test
    public void testCalculateChargesForJakartaStreet() {
        Vehicle vehicle = createVehicleForJakartaStreet();
        StreetName streetName = createObjectForJakarta();
        Mockito.when(streetNameRepository.findByStreetName(JAKARTA)).thenReturn(streetName);
        double expectedResult = 6240.0;
        double actualResult = service.calculateCharges(vehicle);
        Assertions.assertNotNull(actualResult);
        Assertions.assertEquals(actualResult, expectedResult);
    }

    public StreetName createObjectForJakarta() {
        StreetName streetName = new StreetName();
        streetName.setId(1);
        streetName.setStreetName(JAKARTA);
        streetName.setPrice(13);
        return streetName;
    }

    private Vehicle createVehicleForJakartaStreet() {

        Vehicle vehicle = new Vehicle();
        vehicle.setId(1);
        String vehicleId = "12345";
        vehicle.setVehicleNumber(vehicleId);
        vehicle.setEntryTime(LocalTime.of(6, 0, 0));
        vehicle.setEntryDate(LocalDate.of(2024, 06, 15));
        vehicle.setExitDate(LocalDate.of(2024, 06, 15));
        vehicle.setExitTime(LocalTime.of(16, 0, 0));
        vehicle.setStatus(COMPLETED);
        vehicle.setStreetName(JAKARTA);
        return vehicle;
    }

    @Test
    public void testCalculateChargesForAzureStreet() {
        Vehicle vehicle = createVehicleForAzureStreet();
        StreetName streetName = createObjectForJakarta();
        Mockito.when(streetNameRepository.findByStreetName(AZURE)).thenReturn(streetName);
        double expectedResult = 60840.0;
        double actualResult = service.calculateCharges(vehicle);
        Assertions.assertNotNull(actualResult);
        Assertions.assertEquals(actualResult, expectedResult);
    }

    private Vehicle createVehicleForAzureStreet() {

        Vehicle vehicle = new Vehicle();
        vehicle.setId(1);
        String vehicleId = "12345";
        vehicle.setVehicleNumber(vehicleId);
        vehicle.setEntryTime(LocalTime.of(22, 0, 0));
        vehicle.setEntryDate(LocalDate.of(2024, 06, 16));
        vehicle.setExitDate(LocalDate.of(2024, 06, 22));
        vehicle.setExitTime(LocalTime.of(21, 0, 0));
        vehicle.setStatus(COMPLETED);
        vehicle.setStreetName(AZURE);

        return vehicle;
    }

    public StreetName createObjectForAzure() {
        StreetName streetName = new StreetName();
        streetName.setId(1);
        streetName.setStreetName(AZURE);
        streetName.setPrice(10);
        return streetName;
    }
}

