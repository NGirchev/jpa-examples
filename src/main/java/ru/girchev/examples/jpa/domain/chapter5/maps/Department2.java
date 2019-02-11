package ru.girchev.examples.jpa.domain.chapter5.maps;

import lombok.Data;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Girchev N.A.
 * Date: 11.02.2019
 */
@Data
@Entity
@Table(schema = "chapter5")
public class Department2 {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy="embeddedTestDepartment", cascade = CascadeType.ALL)
    private Map<EmployeeName2, Employee2> embeddedTestEmployee
            = new HashMap<EmployeeName2, Employee2>();
}
