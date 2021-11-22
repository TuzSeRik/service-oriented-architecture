package dev.tuzserik.service.oriented.architecture.lab1.server.utils;

import dev.tuzserik.service.oriented.architecture.lab1.server.model.FuelType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@Data @AllArgsConstructor @NoArgsConstructor
public class QueryParameters {
    private String id = null;
    private ComparisonSign idSign = null;

    private String name = null;
    private Boolean isExactName = null;

    private String coordinateX = null;
    private ComparisonSign xSign = null;

    private String coordinateY = null;
    private ComparisonSign ySign = null;

    private String enginePower = null;
    private ComparisonSign engineSign = null;

    private String numberOfWheels = null;
    private ComparisonSign wheelsSign = null;

    private String distanceTravelled = null;
    private ComparisonSign distanceSign = null;

    private FuelType fuelType = null;

    private String sortField = null;
    private Boolean isDesc = false;

    private int page = 0;
    private int limit = 100;

    public QueryParameters(HttpServletRequest request) {
        id = request.getParameter("id");
        idSign = (id != null ?
                ComparisonSign.valueOf(request.getParameter("id-sign").toUpperCase(Locale.ROOT)) : null);

        name = request.getParameter("name");
        isExactName = (name != null ?
                ComparisonSign.valueOf(request.getParameter("name-sign").toUpperCase(Locale.ROOT)) == ComparisonSign.LK : null);

        coordinateX = request.getParameter("coordinates-x");
        xSign = (xSign != null ?
                ComparisonSign.valueOf(request.getParameter("x-sign").toUpperCase(Locale.ROOT)) : null);

        coordinateY = request.getParameter("coordinates-y");
        ySign = (ySign != null ?
                ComparisonSign.valueOf(request.getParameter("y-sign").toUpperCase(Locale.ROOT)) : null);

        enginePower = request.getParameter("engine-power");
        engineSign = (enginePower != null ?
                ComparisonSign.valueOf(request.getParameter("engine-sign").toUpperCase(Locale.ROOT)) : null);

        numberOfWheels = request.getParameter("number-of-wheels");
        wheelsSign = (numberOfWheels != null ?
                ComparisonSign.valueOf(request.getParameter("wheels-sign").toUpperCase(Locale.ROOT)) : null);

        distanceTravelled = request.getParameter("distance-travelled");
        distanceSign = (distanceTravelled != null ?
                ComparisonSign.valueOf(request.getParameter("distance-sign").toUpperCase(Locale.ROOT)) : null);

        fuelType = (request.getParameter("fuel-type") != null ?
                FuelType.valueOf(request.getParameter("fuel-type")) : null);

        sortField = request.getParameter("sort-field");
        isDesc = (sortField != null ?
                ComparisonSign.valueOf(request.getParameter("sort-sign")) == ComparisonSign.LS : null);

        limit = (request.getParameter("limit") != null ?
                Integer.parseInt(request.getParameter("limit")) : 10);

        page = (request.getParameter("page") != null ?
                Integer.parseInt(request.getParameter("page")) : 0);
    }
}
