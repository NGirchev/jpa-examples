package ru.girchev.examples.jpa.domain.chapter8;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Girchev N.A.
 * Date: 13.02.2019
 */
@Data
@Entity
@ToString(exclude = {"owners", "garage"})
@Table(schema = "chapter8")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Car extends AbstractEntity {

    private String name;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Garage garage;

    @ManyToMany(mappedBy = "cars")
    private List<CarOwner> owners = new ArrayList<>();

    @Embedded
    private Specification specification;
}
