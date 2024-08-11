package com.infy.parking.assignment.jpa.controller;

import com.infy.parking.assignment.jpa.entity.ParkedVehicles;
import com.infy.parking.assignment.jpa.service.MonitorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class MonitoringSchedulerTest {
    @InjectMocks
    private MonitoringScheduler controller;
    @Mock
    private MonitorService service;

    @Test
    public  void testCheckUnregisteredVehicle() throws IOException {
        List<ParkedVehicles> list =createListForParkedVehicles();
        Mockito.when(service.checkUnregisteredVehicles()).thenReturn(list);
        List<ParkedVehicles> actualOutput=controller.checkUnregisteredVehicle();
        Assertions.assertNotNull(actualOutput);
        Assertions.assertEquals(actualOutput.get(0).getVehicleNumber(),"12345");
    }

    private List<ParkedVehicles> createListForParkedVehicles() {
        ParkedVehicles parkedVehicles=new ParkedVehicles();
        parkedVehicles.setId(1);
        parkedVehicles.setNameOfOwner("Unmesh");
        parkedVehicles.setAddress("Netherlands");
        parkedVehicles.setVehicleNumber("12345");
        List<ParkedVehicles> list= Arrays.asList(parkedVehicles);

        return list;
    }
}
