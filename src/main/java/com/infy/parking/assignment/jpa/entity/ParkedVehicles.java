package com.infy.parking.assignment.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "parked_vehicle")
@Getter
@Setter
@AllArgsConstructor
public class ParkedVehicles {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String vehicleNumber;
    private String streetname;
    private LocalDate dateOfObservation;
    private LocalTime timeOfObservation;
    private String address;
    private boolean isEliigibleForFine;
    private double fineAmount;
    private String nameOfOwner;

    public String getNameOfOwner() {
        return nameOfOwner;
    }

    public void setNameOfOwner(String nameOfOwner) {
        this.nameOfOwner = nameOfOwner;
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

    public String getStreetname() {
        return streetname;
    }

    public void setStreetname(String streetname) {
        this.streetname = streetname;
    }

    public LocalDate getDateOfObservation() {
        return dateOfObservation;
    }

    public void setDateOfObservation(LocalDate dateOfObservation) {
        this.dateOfObservation = dateOfObservation;
    }

    public LocalTime getTimeOfObservation() {
        return timeOfObservation;
    }

    public void setTimeOfObservation(LocalTime timeOfObservation) {
        this.timeOfObservation = timeOfObservation;
    }

    public boolean isEliigibleForFine() {
        return isEliigibleForFine;
    }

    public void setEliigibleForFine(boolean eliigibleForFine) {
        isEliigibleForFine = eliigibleForFine;
    }

    public double getFineAmount() {
        return fineAmount;
    }

    public void setFineAmount(double fineAmount) {
        this.fineAmount = fineAmount;
    }

    public ParkedVehicles() {

    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
