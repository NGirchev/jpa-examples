package ru.girchev.examples.jpa.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * @author Girchev N.A.
 * Date: 19.02.2019
 */
@Data
@Entity
public class Address2 {

    @Id @GeneratedValue
    Long id;
}
