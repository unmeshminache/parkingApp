package com.infy.parking.assignment.jpa.exception;

import java.util.NoSuchElementException;

public class VehicleRegistartionNotFoundException extends NoSuchElementException {
    public VehicleRegistartionNotFoundException(String errorMessage) {
        super(errorMessage);
    }

}