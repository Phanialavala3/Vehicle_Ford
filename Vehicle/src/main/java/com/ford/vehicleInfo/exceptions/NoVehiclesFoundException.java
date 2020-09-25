package com.ford.vehicleInfo.exceptions;

public class NoVehiclesFoundException extends RuntimeException {
    public NoVehiclesFoundException(String message) {
        super(message);
    }
}
