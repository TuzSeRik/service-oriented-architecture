package com.baeldung.springsoap.gen;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.time.Duration;
import java.util.stream.Collectors;

@Endpoint
public class ShopEndpoint {
    private static final String NAMESPACE_URI = "http://www.baeldung.com/springsoap/gen";
    private final WebClient serviceOne = WebClient.create("http://localhost:7443");

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "searchByTypeRequest")
    @ResponsePayload
    public SearchByTypeResponse searchByType(@RequestPayload SearchByTypeRequest request) {
        SearchByTypeResponse response = new SearchByTypeResponse();
        response.setVehicle(serviceOne
                .get()
                .uri("/vehicles?fuelType=" + request.getType().toString())
                .retrieve()
                .bodyToMono(VehiclesResponseNew.class)
                .block(Duration.ofSeconds(5))
                .getVehicles().stream().findFirst().get());

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "searchByWheelsRequest")
    @ResponsePayload
    public SearchByWheelsResponse searchByWheels(@RequestPayload SearchByWheelsRequest request) {
        SearchByWheelsResponse response = new SearchByWheelsResponse();
        response.setVehicle(serviceOne
                .get()
                .uri("/vehicles?numberOfWheels=" + 0 + "&wheelsSign=GE")
                .retrieve()
                .bodyToMono(VehiclesResponseNew.class)
                .block(Duration.ofSeconds(5))
                .getVehicles()
                .stream()
                .filter(
                        serviceOne
                                .get()
                                .uri("/vehicles?numberOfWheels=" + request.getTo() + "&wheelsSign=LE")
                                .retrieve()
                                .bodyToMono(VehiclesResponseNew.class)
                                .block(Duration.ofSeconds(5))
                                .getVehicles()::contains
                ).findAny().get());

        return response;
    }
}
