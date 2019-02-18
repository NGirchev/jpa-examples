package ru.girchev.examples.jpa.domain.chapter8;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Girchev N.A.
 * Date: 13.02.2019
 */
@Data
@Entity
@Table(schema = "chapter8")
public class Garage extends AbstractEntity {

    @OneToMany(mappedBy = "garage",
            cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY)
    private List<Car> cars = new ArrayList<>();

    @Override
    public String toString() {
        return "Garage{" +
                "id=" + id +
                ",cars=" + cars +
                '}';
    }
}
