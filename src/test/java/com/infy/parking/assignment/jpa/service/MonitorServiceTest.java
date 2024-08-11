package com.infy.parking.assignment.jpa.service;

import com.infy.parking.assignment.jpa.entity.Address;
import com.infy.parking.assignment.jpa.entity.ParkedVehicles;
import com.infy.parking.assignment.jpa.repository.AddressRepository;
import com.infy.parking.assignment.jpa.repository.ParkedVehiclesRepository;
import com.infy.parking.assignment.jpa.repository.ParkingRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MonitorServiceTest {

    @InjectMocks
    private MonitorService monitorService;
    @Mock
    private ParkedVehiclesRepository parkedVehiclesRepository;
    @Mock
    private AddressRepository addressRepository;
    @Mock
    private ParkingRepository parkingRepository;

    @Test
    public void testCheckUnregisteredVehicles() throws IOException {
        List<ParkedVehicles> list= createListForParkedVehicles();
        when(parkedVehiclesRepository.findAll()).thenReturn(list);
        when(parkingRepository.findByVehicleNumberAndStatusIsActive(anyString())).thenReturn(null);
        when(addressRepository.findByVehicleNumber(anyString())).thenReturn(createAddressObject());
        List<ParkedVehicles> actualOutput=monitorService.checkUnregisteredVehicles();
        Assertions.assertNotNull(actualOutput);
        Assertions.assertEquals(actualOutput.get(0).getVehicleNumber(),"12345");
    }
    @Test
    public void testRegisterMonitoredVehicle(){
        List<ParkedVehicles> parkedVehicles=createListForParkedVehicles();
        MonitorService monitorService1=mock(MonitorService.class);
        doNothing().when(monitorService1).registerMonitoredVehicle(parkedVehicles);
        monitorService1.registerMonitoredVehicle(parkedVehicles);
        verify(monitorService1, times(1)).registerMonitoredVehicle(parkedVehicles);
    }

    public List<ParkedVehicles> createListForParkedVehicles() {
        ParkedVehicles parkedVehicles=new ParkedVehicles();
        parkedVehicles.setId(1);
        parkedVehicles.setNameOfOwner("Unmesh");
        parkedVehicles.setAddress("Netherlands");
        parkedVehicles.setVehicleNumber("12345");
        List<ParkedVehicles> list= Arrays.asList(parkedVehicles);
        return list;
    }
    public Address createAddressObject(){
        Address address = new Address();
        address.setAddressId(1);
        address.setVehicleNumber("12345");
        address.setAddress("Netherlands");
        return  address;
    }

}