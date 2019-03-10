package ru.girchev.examples.jpa.domain;

import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author Girchev N.A.
 * Date: 19.02.2019
 */
@Data
//@Embeddable
public class AddressNew implements Serializable{

//    @Id @GeneratedValue
    Long id1;
}
