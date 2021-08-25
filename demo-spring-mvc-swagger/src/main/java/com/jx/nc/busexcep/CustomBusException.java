package com.jx.nc.busexcep;

public class CustomBusException extends RuntimeException {

    public CustomBusException(String errMsg) {
        super(errMsg);
    }
}
