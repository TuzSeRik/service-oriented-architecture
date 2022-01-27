package dev.tuzserik.service.oriented.architecture.lab2.service.one.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Random;

@AllArgsConstructor @NoArgsConstructor @Getter @Setter @ToString @Entity
public class Coordinates {
    @Id
    private long id = new Random().nextInt(999) + 1;

    @Min(value = -885, message = "Значение поля должно быть больше -885")
    private long x;

    @NotNull(message = "Поле не может быть null")
    @Min(value = -29, message = "Значение поля должно быть больше -29")
    private Float y;
}
