package ru.girchev.examples.jpa.domain.chapter5;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Girchev N.A.
 * Date: 10.02.2019
 */
@Data
@Entity
@ToString(exclude = {"personList"})
@Table(schema = "chapter5")
public class Cat {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToMany(mappedBy = "cats")
    private List<Person> personList = new ArrayList<Person>();

    @Column(nullable = false, updatable = false)
    private String name;

    public Cat() {
    }

    public Cat(String name) {
        this.name = name;
    }
}
