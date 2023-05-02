package com.zzk.springboot.annotation.generic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CarHandler {

    @Autowired
    @CarQualifier
    private List<Vehicle> vehicles;

    public List<Vehicle> getVehicles() throws NoSuchFieldException {
        return this.vehicles;
    }
}
