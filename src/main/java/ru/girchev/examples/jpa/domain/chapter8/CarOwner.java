package ru.girchev.examples.jpa.domain.chapter8;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Girchev N.A.
 * Date: 13.02.2019
 */
@Data
@Entity
@Table(schema = "chapter8")
public class CarOwner extends AbstractEntity{

    private String name;

    @ManyToMany
    @JoinTable(schema = "chapter8")
    private List<Car> cars = new ArrayList<>();
}
