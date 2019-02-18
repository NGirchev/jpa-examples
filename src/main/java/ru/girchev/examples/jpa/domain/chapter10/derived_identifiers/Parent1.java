package ru.girchev.examples.jpa.domain.chapter10.derived_identifiers;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Girchev N.A.
 * Date: 13.02.2019
 */
@Entity
@Table(schema = "chapter10")
public class Parent1 {

    @Id
    @GeneratedValue
    private Long id;

}
