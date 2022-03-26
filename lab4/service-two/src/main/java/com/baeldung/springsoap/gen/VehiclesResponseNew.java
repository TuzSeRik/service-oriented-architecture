package com.baeldung.springsoap.gen;

import com.baeldung.springsoap.gen.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor @AllArgsConstructor @Data
public class VehiclesResponseNew {
    private List<Vehicle> vehicles;
}
