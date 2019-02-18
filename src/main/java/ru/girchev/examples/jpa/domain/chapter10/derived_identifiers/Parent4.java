package ru.girchev.examples.jpa.domain.chapter10.derived_identifiers;

import lombok.Data;
import ru.girchev.examples.jpa.domain.chapter10.EmployeeId;

import javax.persistence.*;

/**
 * @author Girchev N.A.
 * Date: 13.02.2019
 */
@Data
@Entity
@Table(schema = "chapter10")
public class Parent4 {

    @Id
    @GeneratedValue
    private Long id;
}
