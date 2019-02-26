package ru.girchev.examples.jpa.domain.chapter12;

import lombok.Data;

import javax.persistence.Column;

/**
 * @author Girchev N.A.
 * Date: 17.02.2019
 */
@Data
public class Employee12 extends Abstr {

    private Long id;

    @Column(name = "NAME2")
    private String name;

}
