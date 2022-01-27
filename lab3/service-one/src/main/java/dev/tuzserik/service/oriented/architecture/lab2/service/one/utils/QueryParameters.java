package dev.tuzserik.service.oriented.architecture.lab2.service.one.utils;

import dev.tuzserik.service.oriented.architecture.lab2.service.one.model.FuelType;
import lombok.Data;

@Data
public class QueryParameters {
    private Long id;
    private ComparisonSign idSign;

    private String name;
    private ComparisonSign nameSign;

    private Long coordinateX;
    private ComparisonSign xSign;

    private Float coordinateY;
    private ComparisonSign ySign;

    private Float enginePower;
    private ComparisonSign engineSign;

    private Integer numberOfWheels;
    private ComparisonSign wheelsSign;

    private Double distanceTravelled;
    private ComparisonSign distanceSign;

    private FuelType fuelType;

    private String sortField;
    private Boolean isDesc = false;

    private Integer page = 0;
    private Integer limit = 100;
}
