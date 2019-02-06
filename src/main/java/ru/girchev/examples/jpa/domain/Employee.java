package ru.girchev.examples.jpa.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Girchev N.A.
 * Date: 05.02.2019
 */
@Data
@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @SequenceGenerator(name = "empl_gen", sequenceName = "empl_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "empl_gen")
    private Long id;

    @Column(name = "name")
    private String name;
}
