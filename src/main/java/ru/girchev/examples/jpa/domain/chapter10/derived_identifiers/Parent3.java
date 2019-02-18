package ru.girchev.examples.jpa.domain.chapter10.derived_identifiers;

import ru.girchev.examples.jpa.domain.chapter10.EmployeeId;

import javax.persistence.*;

/**
 * @author Girchev N.A.
 * Date: 13.02.2019
 */
@Entity
@Table(schema = "chapter10")
@IdClass(EmployeeId.class)
public class Parent3 {

    @Id
    @GeneratedValue
    private Long id;

    @Id
    private String country;

}
