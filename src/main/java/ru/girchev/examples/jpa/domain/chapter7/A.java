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
public class A {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(schema = "chapter7")
    private List<B> list = new ArrayList<>();

    public A() {
    }

    public A(List<B> list) {
        this.list = list;
        list.stream().forEach(b -> b.setA(this));
    }
}
