package com.infy.parking.assignment.jpa.controller;

import com.infy.parking.assignment.jpa.entity.Vehicle;
import com.infy.parking.assignment.jpa.exception.VehicleRegistartionNotFoundException;
import com.infy.parking.assignment.jpa.service.ParkingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.time.LocalDate;
import java.time.LocalTime;

@ExtendWith(MockitoExtension.class)
public class ParkingControllerTest {

    @InjectMocks
    private ParkingController controller;
    @Mock
    private ParkingService service;

    @Test
    public void testGetVehicle() throws VehicleRegistartionNotFoundException {
        String vehicleNumber = "12345";
        Vehicle vehicle = createVehicle();
        Mockito.when(service.getVehicle(vehicleNumber)).thenReturn(vehicle);
        ResponseEntity<Object> actualResult = controller.getVehicle(vehicleNumber);
        ResponseEntity<Object> expectedResult = new ResponseEntity<>(vehicle, HttpStatus.OK);
        Assertions.assertNotNull(actualResult);
        Assertions.assertEquals(expectedResult, actualResult);
        Assertions.assertEquals(expectedResult.getStatusCode(), actualResult.getStatusCode());
    }

    @Test
    public void testRegisterVehicle() throws VehicleRegistartionNotFoundException {
        Vehicle vehicle = createVehicle();
        Mockito.when(service.registerVehicle(vehicle)).thenReturn(vehicle);
        ResponseEntity<Object> actualResult = controller.registerVehicle(vehicle);
        String message = "Your parking Session has begun with vehicle number : " + vehicle.getVehicleNumber();
        ResponseEntity<Object> expectedResult = new ResponseEntity<>(message, HttpStatus.OK);
        Assertions.assertNotNull(actualResult);
        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testDeRegisterVehicle() throws VehicleRegistartionNotFoundException {
        Vehicle vehicle = createVehicle();
        Mockito.when(service.updateDetails(vehicle)).thenReturn(500.0);
        ResponseEntity<Object> actualResult = controller.deregisterVehicle(vehicle);
        String message = "Total parking charges for your vehicle is : " + 500.0 + " Cents";
        ResponseEntity<Object> expectedResult = new ResponseEntity<>(message, HttpStatus.OK);
        Assertions.assertNotNull(actualResult);
        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testDeRegisterVehicleForNullDateTime() throws VehicleRegistartionNotFoundException {
        String vehicleNumber = "12345";
        Vehicle vehicle = createVehicleForLocalDateTimeISNull();
        Mockito.when(service.updateDetails(vehicle)).thenReturn(100.0);
        String message = "Total parking charges for your vehicle is : " + 100.0 + " Cents";
        ResponseEntity<Object> actualResult = controller.deregisterVehicle(vehicle);
        ResponseEntity<Object> expectedResult = new ResponseEntity<>(message, HttpStatus.OK);

        Assertions.assertNotNull(actualResult);
        Assertions.assertEquals(expectedResult, actualResult);
        Assertions.assertEquals(expectedResult.getStatusCode(), actualResult.getStatusCode());
    }

    @Test
    public void testRegisterVehicleForNullDateTime() throws VehicleRegistartionNotFoundException {
        String vehicleNumber = "12345";
        Vehicle vehicle = createVehicleForLocalDateTimeISNull();
        ResponseEntity<Object> actualResult = controller.registerVehicle(vehicle);
        String message = "Your parking Session has begun with vehicle number : " + vehicle.getVehicleNumber();
        ResponseEntity<Object> expectedResult = new ResponseEntity<>(message, HttpStatus.OK);

        Assertions.assertNotNull(actualResult);
        Assertions.assertEquals(expectedResult, actualResult);
        Assertions.assertEquals(expectedResult.getStatusCode(), actualResult.getStatusCode());
    }

    private Vehicle createVehicle() {
        Vehicle vehicle = new Vehicle();
        String vehicleNumber = "12345";
        String COMPLETED = "Completed";
        vehicle.setVehicleNumber(vehicleNumber);
        vehicle.setEntryTime(LocalTime.of(12, 0, 0));
        vehicle.setEntryDate(LocalDate.of(2024, 06, 14));
        vehicle.setExitDate(LocalDate.of(2024, 06, 15));
        vehicle.setExitTime(LocalTime.of(10, 0, 0));
        vehicle.setStatus(COMPLETED);
        return vehicle;
    }

    private Vehicle createVehicleForLocalDateTimeISNull() {

        Vehicle vehicle = new Vehicle();
        String vehicleNumber = "12345";
        String COMPLETED = "Completed";
        vehicle.setVehicleNumber(vehicleNumber);
        vehicle.setStatus(COMPLETED);
        return vehicle;
    }
}
