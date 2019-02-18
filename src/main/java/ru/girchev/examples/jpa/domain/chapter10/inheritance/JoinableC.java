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
@Inheritance(strategy = InheritanceType.JOINED) //default
@DiscriminatorColumn(name = "DTYPE", discriminatorType = DiscriminatorType.STRING)
public class JoinableC {

    @Id
    @GeneratedValue
    Long id;

    String c;
}
