package dev.tuzserik.service.oriented.architecture.lab2.service.one.controllers;

import dev.tuzserik.service.oriented.architecture.lab2.service.one.persistence.Datasource;
import dev.tuzserik.service.oriented.architecture.lab2.service.one.responses.VehiclesResponse;
import dev.tuzserik.service.oriented.architecture.lab2.service.one.utils.QueryBuilder;
import org.hibernate.Session;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@RestController @EnableWebMvc
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class DistanceTravelledSearcher {
    @GetMapping("/distance-travelled-searcher")
    ResponseEntity<VehiclesResponse> doGet(@RequestParam Double distance) {
        try (Session session = Datasource.getSessionFactory().openSession()) {
            Datasource.flushCache(session);

            return new ResponseEntity<>(
                    new VehiclesResponse(
                            QueryBuilder.getPreparedQueryDistanceTraveledLessThan(session, distance)
                                    .getResultList()),
                    HttpStatus.OK
            );
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
