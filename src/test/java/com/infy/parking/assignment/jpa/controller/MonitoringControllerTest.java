package com.infy.parking.assignment.jpa.controller;

import com.infy.parking.assignment.jpa.entity.ParkedVehicles;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MonitoringControllerTest {

    @Test
    public void testMonitoredVehiclesInParking() throws IOException {
        List<ParkedVehicles> list=createListForParkedVehicles();
        MonitoringController monitoringController=mock(MonitoringController.class);
        doNothing().when(monitoringController).monitoredVehiclesInParking(list);
        monitoringController.monitoredVehiclesInParking(list);
        verify(monitoringController, times(1)).monitoredVehiclesInParking(list);
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
