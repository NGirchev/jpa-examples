package ru.girchev.examples.jpa.domain.chapter10.inheritance;

import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Girchev N.A.
 * Date: 17.02.2019
 */
@Data
@Entity
@Table(schema = "chapter10") //ignored
@DiscriminatorValue("E1")
public class E1 extends C1 {

    private String e1;
}
