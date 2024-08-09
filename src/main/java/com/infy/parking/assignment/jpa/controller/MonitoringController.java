package com.infy.parking.assignment.jpa.controller;

import com.infy.parking.assignment.jpa.entity.ParkedVehicles;
import com.infy.parking.assignment.jpa.service.MonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/monitor/")
public class MonitoringController {

    @Autowired
    private final MonitorService service;

    public MonitoringController(MonitorService service) {
        this.service = service;
    }


    @PostMapping
    @RequestMapping("/registervehicle/")
    public void monitoredVehiclesInParking(@RequestBody List<ParkedVehicles> parkedVehicles) {
        service.registerMonitoredVehicle(parkedVehicles);
    }

}
