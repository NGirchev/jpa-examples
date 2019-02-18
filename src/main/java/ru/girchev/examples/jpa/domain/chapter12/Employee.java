package ru.girchev.examples.jpa.domain.chapter12;

import lombok.Data;

import javax.persistence.Column;
import java.util.List;

/**
 * @author Girchev N.A.
 * Date: 17.02.2019
 */
@Data
public class Employee extends Abstr {

    private Long id;

    @Column(name = "NAME2")
    private String name;

}
