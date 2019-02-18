package ru.girchev.examples.jpa.domain.chapter7;

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
@Table(schema = "chapter7")
public class A2 {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JoinTable(schema = "chapter7")
    private List<B2> list = new ArrayList<>();

    public A2() {
    }

    public A2(List<B2> list) {
        this.list = list;
        list.stream().forEach(b -> b.setA2(this));
    }
}
