package com.infy.parking.assignment.jpa.controller;

import com.infy.parking.assignment.jpa.entity.ParkedVehicles;
import com.infy.parking.assignment.jpa.service.MonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class MonitoringScheduler {

    @Autowired
    private final MonitorService service;

    public MonitoringScheduler(MonitorService service) {
        this.service = service;
    }

    @Scheduled(cron = "0 8 * * * *")
    public List<ParkedVehicles> checkUnregisteredVehicle() throws IOException {
        return service.checkUnregisteredVehicles();
    }
}
