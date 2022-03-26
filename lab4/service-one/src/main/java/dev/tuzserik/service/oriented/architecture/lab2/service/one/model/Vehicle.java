package dev.tuzserik.service.oriented.architecture.lab2.service.one.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

@AllArgsConstructor @NoArgsConstructor @Getter @Setter @ToString @Entity
public class Vehicle {
    @Id @NotNull(message = "Поле не может быть null")
    @Min(value = 0, message = "Значение поля должно быть больше 0")
    private long id = new Random().nextInt(999) + 1;

    @NotNull(message = "Поле не может быть null")
    @Size(min = 1, message = "Строка не может быть пустой")
    private String name;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @NotNull(message = "Поле не может быть null")
    private Coordinates coordinates;

    @NotNull(message = "Поле не может быть null")
    private Date creationDate = new Date();

    @Min(value = 0, message = "Значение поля должно быть больше 0")
    private float enginePower;

    @NotNull(message = "Поле не может быть null")
    @Min(value = 0, message = "Значение поля должно быть больше 0")
    private Integer numberOfWheels;

    @Min(value = 0, message = "Значение поля должно быть больше 0")
    private Double distanceTravelled;

    @Enumerated(value = EnumType.ORDINAL) @NotNull(message = "Поле не может быть null")
    private FuelType fuelType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Vehicle vehicle = (Vehicle) o;
        return Objects.equals(id, vehicle.id);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
