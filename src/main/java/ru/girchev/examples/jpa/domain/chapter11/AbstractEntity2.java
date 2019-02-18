package ru.girchev.examples.jpa.domain.chapter11;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Girchev N.A.
 * Date: 13.02.2019
 */
@Data
@MappedSuperclass
public abstract class AbstractEntity2 implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @Version
    private Integer version;

    @PostPersist
    private void postPersist2() {
        System.out.println("SUPER LISTENER = " + id);
    }
}
