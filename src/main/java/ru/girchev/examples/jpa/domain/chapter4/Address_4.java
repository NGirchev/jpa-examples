package ru.girchev.examples.jpa.domain.chapter4;

import lombok.Data;

import javax.persistence.Embeddable;

/**
 * @author Girchev N.A.
 * Date: 10.02.2019
 */
@Data
@Embeddable
public class Address_4 {

    private String street;
    private String building;
}
