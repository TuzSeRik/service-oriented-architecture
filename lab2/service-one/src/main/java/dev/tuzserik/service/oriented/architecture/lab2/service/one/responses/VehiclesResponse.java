package dev.tuzserik.service.oriented.architecture.lab2.service.one.responses;

import dev.tuzserik.service.oriented.architecture.lab2.service.one.model.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor @Data
public class VehiclesResponse {
    private List<Vehicle> vehicles;
}
