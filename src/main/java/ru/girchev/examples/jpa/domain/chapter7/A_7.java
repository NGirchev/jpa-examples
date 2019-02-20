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
public class A_7 {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(schema = "chapter7")
    private List<B_7> list = new ArrayList<>();

    public A_7() {
    }

    public A_7(List<B_7> list) {
        this.list = list;
        list.stream().forEach(b -> b.setA(this));
    }
}
