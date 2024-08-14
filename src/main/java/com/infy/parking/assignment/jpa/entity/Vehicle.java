package com.infy.parking.assignment.jpa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "parking")
public class Vehicle {

    @Id
    @GeneratedValue
    private int id;
    @NotNull(message = "Vehicle number Should not be null")
    @Size(min = 4, max=10, message = "Vehicle Number should be equal or grater than 4 character and less or equal to 10 character")
    @Column(name = "vehicle_number")
    private String vehicleNumber;
    @Column(name = "entry_Date")
    private LocalDate entryDate;
    @Column(name = "entry_time")
    private LocalTime entryTime;
    @Column(name = "exit_Date")
    private LocalDate exitDate;
    @Column(name = "exit_time")
    private LocalTime exitTime;
    @Column(name = "status")
    private String status;
    @Column(name = "street_name")
    private String streetName;

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
    }

    public LocalTime getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(LocalTime entryTime) {
        this.entryTime = entryTime;
    }

    public LocalDate getExitDate() {
        return exitDate;
    }

    public void setExitDate(LocalDate exitDate) {
        this.exitDate = exitDate;
    }

    public LocalTime getExitTime() {
        return exitTime;
    }

    public void setExitTime(LocalTime exitTime) {
        this.exitTime = exitTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
