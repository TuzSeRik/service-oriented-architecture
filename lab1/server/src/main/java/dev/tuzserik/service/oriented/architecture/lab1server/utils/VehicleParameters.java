package dev.tuzserik.service.oriented.architecture.lab1server.utils;

import dev.tuzserik.service.oriented.architecture.lab1server.model.Coordinates;
import dev.tuzserik.service.oriented.architecture.lab1server.model.FuelType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.servlet.http.HttpServletRequest;

@Data @AllArgsConstructor @NoArgsConstructor
public class VehicleParameters {
    private String name;
    private Coordinates coordinates;
    private float enginePower;
    private Integer numberOfWheels;
    private Double distanceTravelled;
    private FuelType fuelType;

    public VehicleParameters(HttpServletRequest request) {
        name = request.getParameter("name");
        coordinates = new Coordinates()
                .setX(Long.parseLong(request.getParameter("coordinates-x")))
                .setY(Float.parseFloat(request.getParameter("coordinates-y")));
        enginePower = Float.parseFloat(request.getParameter("engine-power"));
        numberOfWheels = Integer.parseInt(request.getParameter("number-of-wheels"));
        distanceTravelled = Double.parseDouble(request.getParameter("distance-travelled"));
        fuelType = FuelType.valueOf(request.getParameter("fuel-type"));
    }
}
