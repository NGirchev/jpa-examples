package ru.girchev.examples.jpa.domain.chapter12;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Girchev N.A.
 * Date: 17.02.2019
 */
@Data
@Entity
@Table(schema = "chapter12")
public class Car12 {

    @Id
    private Long id;

    private String name;

}
