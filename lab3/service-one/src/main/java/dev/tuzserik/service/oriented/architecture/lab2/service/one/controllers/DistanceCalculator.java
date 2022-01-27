package dev.tuzserik.service.oriented.architecture.lab2.service.one.controllers;

import dev.tuzserik.service.oriented.architecture.lab2.service.one.persistence.Datasource;
import dev.tuzserik.service.oriented.architecture.lab2.service.one.utils.QueryBuilder;
import org.hibernate.Session;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@RestController @EnableWebMvc
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class DistanceCalculator {
    @GetMapping("/distance-calculator")
    ResponseEntity<Double> doGet() {
        try (Session session = Datasource.getSessionFactory().openSession()) {
            Datasource.flushCache(session);

            return new ResponseEntity<>(
                    QueryBuilder.getPreparedQueryDistanceSum(session).getSingleResult(),
                    HttpStatus.OK
            );
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
