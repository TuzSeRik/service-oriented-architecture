package dev.tuzserik.service.oriented.architecture.lab2.service.one.controllers;

import dev.tuzserik.service.oriented.architecture.lab2.service.one.model.Coordinates;
import dev.tuzserik.service.oriented.architecture.lab2.service.one.model.FuelType;
import dev.tuzserik.service.oriented.architecture.lab2.service.one.model.Vehicle;
import dev.tuzserik.service.oriented.architecture.lab2.service.one.persistence.Datasource;
import dev.tuzserik.service.oriented.architecture.lab2.service.one.requests.PostVehicleRequest;
import dev.tuzserik.service.oriented.architecture.lab2.service.one.requests.PutVehicleRequest;
import dev.tuzserik.service.oriented.architecture.lab2.service.one.responses.VehiclesResponse;
import dev.tuzserik.service.oriented.architecture.lab2.service.one.utils.ComparisonSign;
import dev.tuzserik.service.oriented.architecture.lab2.service.one.utils.QueryBuilder;
import dev.tuzserik.service.oriented.architecture.lab2.service.one.utils.QueryParameters;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.validation.Validation;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController @EnableWebMvc
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class BasicOperationsPerformer {
    @PostMapping("/vehicles")
    ResponseEntity<VehiclesResponse> doPost(@RequestBody PostVehicleRequest request) {
        Transaction transaction;

        try (Session session = Datasource.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Vehicle vehicle =
                    new Vehicle()
                            .setName(request.getName())
                            .setCoordinates(
                                    new Coordinates()
                                            .setX(request.getCoordinates().getX())
                                            .setY(request.getCoordinates().getY()))
                            .setEnginePower(request.getEnginePower())
                            .setNumberOfWheels(request.getNumberOfWheels())
                            .setDistanceTravelled(request.getDistanceTravelled())
                            .setFuelType(request.getFuelType());

            // if (!Validation.buildDefaultValidatorFactory().getValidator().validate(vehicle).isEmpty())
            //     throw new Exception("Validation error!");

            Datasource.cachedVehicles.add(vehicle);

            transaction.commit();

            return new ResponseEntity<>(
                    new VehiclesResponse(Collections.singletonList(vehicle)), HttpStatus.OK
            );
        } catch (Exception exception) {
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/vehicles/{id}")
    ResponseEntity<VehiclesResponse> doGetSingle(@PathVariable long id) {
        List<Vehicle> filteredVehicles =
                Datasource.cachedVehicles.stream().filter(vehicle -> vehicle.getId() == id)
                        .collect(Collectors.toList());

        if (filteredVehicles.size() >= 1) {
            return new ResponseEntity<>(
                    new VehiclesResponse(Collections.singletonList(filteredVehicles.get(0))),
                    HttpStatus.OK
            );
        }
        else {
            try (Session session = Datasource.getSessionFactory().openSession()) {
                List<Vehicle> particularVehicle = QueryBuilder.getPreparedQuerySingleId(
                        session, id
                ).getResultList();

                if (particularVehicle.size() >= 1) {
                    return new ResponseEntity<>(
                            new VehiclesResponse(Collections.singletonList(particularVehicle.get(0))),
                            HttpStatus.OK
                    );
                }
            }
            catch (Exception exception) {
                exception.printStackTrace();
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            return new ResponseEntity<>(new VehiclesResponse(Collections.emptyList()), HttpStatus.OK);
        }
    }

    @GetMapping("/vehicles")
    ResponseEntity<VehiclesResponse> doGet(
            @RequestParam(required = false) Long id, @RequestParam(required = false) ComparisonSign idSign,
            @RequestParam(required = false) String name, @RequestParam(required = false) ComparisonSign nameSign,
            @RequestParam(required = false) Long coordinateX, @RequestParam(required = false) ComparisonSign xSign,
            @RequestParam(required = false) Float coordinateY, @RequestParam(required = false) ComparisonSign ySign,
            @RequestParam(required = false) Float enginePower, @RequestParam(required = false) ComparisonSign engineSign,
            @RequestParam(required = false) Integer numberOfWheels, @RequestParam(required = false) ComparisonSign wheelsSign,
            @RequestParam(required = false) Double distanceTravelled, @RequestParam(required = false) ComparisonSign distanceSign,
            @RequestParam(required = false) FuelType fuelType,
            @RequestParam(required = false) String sortField, @RequestParam(required = false) Boolean isDesc,
            @RequestParam(required = false) Integer page, @RequestParam(required = false) Integer limit
    ){
        try (Session session = Datasource.getSessionFactory().openSession()) {
            Datasource.flushCache(session);

            List<Vehicle> allFilteredVehicles = QueryBuilder.getPreparedQuery(
                    new QueryParameters()
                            .setId(id)
                            .setIdSign(idSign)
                            .setName(name)
                            .setNameSign(nameSign)
                            .setCoordinateX(coordinateX)
                            .setXSign(xSign)
                            .setCoordinateY(coordinateY)
                            .setYSign(ySign)
                            .setEnginePower(enginePower)
                            .setEngineSign(engineSign)
                            .setNumberOfWheels(numberOfWheels)
                            .setWheelsSign(wheelsSign)
                            .setDistanceTravelled(distanceTravelled)
                            .setDistanceSign(distanceSign)
                            .setFuelType(fuelType)
                            .setSortField(sortField)
                            .setIsDesc(isDesc)
                            .setPage(page)
                            .setLimit(limit),
                    session)
                        .getResultList();

            return new ResponseEntity<>(
                    new VehiclesResponse(allFilteredVehicles), HttpStatus.OK
            );
        } catch (Exception exception) {
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/vehicles")
    ResponseEntity<VehiclesResponse> doPut(@RequestBody PutVehicleRequest request) {
        Transaction transaction;

        Vehicle vehicle =
                new Vehicle()
                        .setId(request.getId())
                        .setName(request.getName())
                        .setCoordinates(request.getCoordinates())
                        .setEnginePower(request.getEnginePower())
                        .setNumberOfWheels(request.getNumberOfWheels())
                        .setDistanceTravelled(request.getDistanceTravelled())
                        .setFuelType(request.getFuelType());

        try {
            if (!Validation.buildDefaultValidatorFactory().getValidator().validate(vehicle).isEmpty())
                throw new Exception();
        }
        catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<Vehicle> filteredVehicle = Datasource.cachedVehicles.stream()
                .filter(v -> v.getId() == vehicle.getId()).collect(Collectors.toList());

        if (!filteredVehicle.isEmpty()) {
            Datasource.cachedVehicles.set(Datasource.cachedVehicles.indexOf(filteredVehicle.get(0)), vehicle);
        }
        else {
            try (Session session = Datasource.getSessionFactory().openSession()) {
                transaction = session.beginTransaction();
                session.update(vehicle);
                transaction.commit();
                return new ResponseEntity<>(
                        new VehiclesResponse(Collections.singletonList(vehicle)),
                        HttpStatus.OK
                );
            }
            catch (Exception exception) {
                exception.printStackTrace();
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/vehicles")
    ResponseEntity<VehiclesResponse> doDelete(@RequestParam long id) {
        List<Vehicle> filteredVehicle = Datasource.cachedVehicles.stream()
                .filter(v -> v.getId() == id).collect(Collectors.toList());

        if (!filteredVehicle.isEmpty()) {
            Datasource.cachedVehicles.remove(filteredVehicle.get(0));
            return new ResponseEntity<>(
                    new VehiclesResponse(Collections.singletonList(filteredVehicle.get(0))),
                    HttpStatus.OK
            );
        }
        else {
            Transaction transaction;
            try (Session session = Datasource.getSessionFactory().openSession()) {
                Vehicle vehicle = QueryBuilder.getPreparedQuerySingleId(session, id)
                        .getSingleResult();

                if (vehicle != null) {
                    transaction = session.beginTransaction();
                    session.delete(vehicle);
                    session.flush();
                    transaction.commit();
                }
                else
                    return new ResponseEntity<>(
                            new VehiclesResponse(Collections.emptyList()),
                            HttpStatus.OK
                    );

                return new ResponseEntity<>(
                        new VehiclesResponse(Collections.singletonList(vehicle)),
                        HttpStatus.OK
                );
            }
            catch (Exception exception) {
                exception.printStackTrace();
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
    }
}
