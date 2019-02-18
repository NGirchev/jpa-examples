package ru.girchev.examples.jpa.domain.chapter8;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Girchev N.A.
 * Date: 13.02.2019
 */
@Data
@ToString(callSuper = true)
@Entity
@Table(schema = "chapter8")
public class SportCar extends Car {

    private boolean raceCar;
}
