package ru.girchev.examples.jpa.domain.chapter10.inheritance;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Girchev N.A.
 * Date: 16.02.2019
 */
@Data
@Entity
public class B2 extends A {

    String b2;
}
