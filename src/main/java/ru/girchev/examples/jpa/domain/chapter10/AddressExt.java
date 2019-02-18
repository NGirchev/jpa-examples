package ru.girchev.examples.jpa.domain.chapter10;

import lombok.Data;

import javax.persistence.Embeddable;

/**
 * @author Girchev N.A.
 * Date: 13.02.2019
 */
@Data
@Embeddable
public class AddressExt {

    private String street;
    private String city;
    private String building;
}
