package com.infy.parking.assignment.jpa.service;

import com.infy.parking.assignment.jpa.entity.StreetName;
import com.infy.parking.assignment.jpa.entity.Vehicle;
import com.infy.parking.assignment.jpa.exception.VehicleRegistartionNotFoundException;
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
    private final static String ACTIVE = "Active";

    @Test
    public void testGetVehicle() {
        Vehicle expectedResult = createVehicle();
        Mockito.when(repository.findByVehicleNumberAndStatusIsActive(expectedResult.getVehicleNumber())).thenReturn(expectedResult);
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
        Mockito.when(repository.findByVehicleNumberAndStatusIsActive(vehicleFromDAO.getVehicleNumber())).thenReturn(vehicleFromDAO);
        Mockito.when(streetNameRepository.findByStreetName(JAVA)).thenReturn(streetName);
        double actualResult = service.updateDetails(vehicle);
        Assertions.assertNotNull(actualResult);
        Assertions.assertEquals(actualResult, 900.0);
    }

    @Test
    public void testExceptionCalculateMinutes() {
        Vehicle vehicle = createVehicleToTestException();
        Assertions.assertThrows(RuntimeException.class, () -> {
            service.calculateMinutes(vehicle);
        });
    }

    @Test
    public void testExceptionForRegisterVehicle() {
        Vehicle vehicle = createVehicleToTestException();
        Mockito.when(repository.findByVehicleNumberAndStatusIsActive(vehicle.getVehicleNumber())).thenThrow(new VehicleRegistartionNotFoundException("Vehicle not found with given vehicle number :" + vehicle.getVehicleNumber()));
        Assertions.assertThrows(VehicleRegistartionNotFoundException.class, () -> {
            service.getVehicle(vehicle.getVehicleNumber());
        });
    }

    @Test
    public void testExceptionRegisterVehicle() {
        Vehicle vehicle = createVehicleToTestException();
        Mockito.when(repository.findByVehicleNumberAndStatusIsActive(vehicle.getVehicleNumber())).thenReturn(new Vehicle());
        Assertions.assertThrows(RuntimeException.class, () -> {
            service.registerVehicle(vehicle);
        });
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
        String vehicleNumber = "12345";
        vehicle.setVehicleNumber(vehicleNumber);
        vehicle.setEntryTime(LocalTime.of(12, 0, 0));
        vehicle.setEntryDate(LocalDate.of(2024, 06, 14));
        vehicle.setExitDate(LocalDate.of(2024, 06, 15));
        vehicle.setExitTime(LocalTime.of(10, 0, 0));
        vehicle.setStatus(ACTIVE);
        vehicle.setStreetName(JAVA);

        return vehicle;
    }

    public Vehicle createVehicleToTestException() {
        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleNumber("ZZZ");
        vehicle.setExitTime(LocalTime.of(13, 00, 00));
        vehicle.setExitDate(LocalDate.of(2024, 06, 14));
        vehicle.setStreetName("Devops");
        vehicle.setEntryDate(LocalDate.of(2024, 06, 21));
        vehicle.setEntryTime(LocalTime.of(12, 0, 0));
        vehicle.setExitDate(LocalDate.of(2024, 06, 21));
        vehicle.setExitTime(LocalTime.of(11, 0, 0));
        return vehicle;
    }
}

