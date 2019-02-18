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
@DiscriminatorValue("D1")
public class D1 extends B1{

    private String d1;
}
