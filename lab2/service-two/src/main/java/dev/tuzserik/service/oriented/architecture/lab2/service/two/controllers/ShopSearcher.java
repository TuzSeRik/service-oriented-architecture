package dev.tuzserik.service.oriented.architecture.lab2.service.two.controllers;

import dev.tuzserik.service.oriented.architecture.lab2.service.two.model.FuelType;
import dev.tuzserik.service.oriented.architecture.lab2.service.two.responses.VehiclesResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.time.Duration;
import java.util.stream.Collectors;

@RestController @EnableWebMvc
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class ShopSearcher {
    private final WebClient serviceOne = WebClient.create("https://localhost:8181/service-one");

    @GetMapping(value = "/search/by-type/{type}")
    ResponseEntity<VehiclesResponse> searchByType(@PathVariable FuelType type) {
        try {
            return new ResponseEntity<>(
                    new VehiclesResponse(
                            serviceOne
                                    .get()
                                    .uri("/vehicles?fuelType=" + type.toString())
                                    .retrieve()
                                    .bodyToMono(VehiclesResponse.class)
                                    .block(Duration.ofSeconds(5))
                                    .getVehicles()
                    ),
                    HttpStatus.OK
            );
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/search/by-number-of-wheels/{from}/{to}")
    ResponseEntity<VehiclesResponse> searchByNumberOfWheels(@PathVariable int from, @PathVariable int to) {
        try {
            return new ResponseEntity<>(
                    new VehiclesResponse(
                                    serviceOne
                                    .get()
                                    .uri("/vehicles?numberOfWheels=" + from + "&wheelsSign=GE")
                                    .retrieve()
                                    .bodyToMono(VehiclesResponse.class)
                                    .block(Duration.ofSeconds(5))
                                    .getVehicles()
                                    .stream()
                                    .filter(
                                            serviceOne
                                                    .get()
                                                    .uri("/vehicles?numberOfWheels=" + to + "&wheelsSign=LE")
                                                    .retrieve()
                                                    .bodyToMono(VehiclesResponse.class)
                                                    .block(Duration.ofSeconds(5))
                                                    .getVehicles()::contains
                                    ).collect(Collectors.toList())
                    ),
                    HttpStatus.OK
            );
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
