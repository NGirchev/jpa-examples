package ru.girchev.examples.jpa.domain.chapter10.inheritance;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Girchev N.A.
 * Date: 16.02.2019
 */
@Data
@Entity
@Table(schema = "chapter10")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) //default
@DiscriminatorColumn(name = "DTYPE", discriminatorType = DiscriminatorType.STRING)
public class A {

    @Id
    @GeneratedValue
    Long id;

    String a;
}
