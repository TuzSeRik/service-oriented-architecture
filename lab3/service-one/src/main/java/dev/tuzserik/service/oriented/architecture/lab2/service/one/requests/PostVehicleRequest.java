package dev.tuzserik.service.oriented.architecture.lab2.service.one.requests;

import dev.tuzserik.service.oriented.architecture.lab2.service.one.model.Coordinates;
import dev.tuzserik.service.oriented.architecture.lab2.service.one.model.FuelType;
import lombok.Data;

@Data
public class PostVehicleRequest {
    private String name;
    private Coordinates coordinates;
    private float enginePower;
    private Integer numberOfWheels;
    private Double distanceTravelled;
    private FuelType fuelType;
}
