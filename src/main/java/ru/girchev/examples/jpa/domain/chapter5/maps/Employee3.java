package ru.girchev.examples.jpa.domain.chapter5.maps;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

/**
 * @author Girchev N.A.
 * Date: 11.02.2019
 */
@Data
@Entity
@ToString(exclude = "embeddedTestDepartment")
@Table(schema = "chapter5")
public class Employee3 {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long fakeId;

    @Embedded
    private EmployeeName3 fullname;
}
