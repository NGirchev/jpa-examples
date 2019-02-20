package ru.girchev.examples.jpa.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Girchev N.A.
 * Date: 18.02.2019
 */
@Data
@Entity
@DiscriminatorColumn(name = "DTYPE")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class AbstractInheritance {
    @Id @GeneratedValue Long id;
    private String val;
}
