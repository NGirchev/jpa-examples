package ru.girchev.examples.jpa.domain.chapter10.inheritance;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Girchev N.A.
 * Date: 16.02.2019
 */
@Data
@Entity
@DiscriminatorValue("B1")
@Table(schema = "chapter10") //ignored
@Inheritance(strategy = InheritanceType.JOINED) //ignored
@DiscriminatorColumn(name = "NEW")
public class B1 extends A {

    //mapped for convenience
    @Id
    Long id;

    String b1;
}
