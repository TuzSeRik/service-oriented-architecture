package dev.tuzserik.service.oriented.architecture.lab2.service.two.responses;

import dev.tuzserik.service.oriented.architecture.lab2.service.two.model.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor @AllArgsConstructor @Data
public class VehiclesResponse {
    private List<Vehicle> vehicles;
}
