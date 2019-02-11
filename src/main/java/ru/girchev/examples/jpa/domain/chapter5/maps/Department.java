package ru.girchev.examples.jpa.domain.chapter5.maps;

import lombok.Data;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Girchev N.A.
 * Date: 10.02.2019
 */
@Data
@Entity
@Table(schema = "chapter5")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    @MapKey(name = "name") //default will be ID of Employee, if type is right. Next field is example!
    // or the same
    // @MapKeyColumn(name = "name") // but can create new column, not only Map to exist
    private Map<String, Employee> employeeMap = new HashMap<String, Employee>();

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    @MapKey
    private Map<Long, Employee> employeeMap4 = new HashMap<Long, Employee>();

    @OneToMany(mappedBy = "department2", cascade = CascadeType.ALL)
    @MapKeyColumn(name = "room", nullable = true) // default false unlike @Column !!!!!
    private Map<String, Employee> employeeMap2 = new HashMap<String, Employee>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            schema = "chapter5",
            name = "department_employee",
            joinColumns = @JoinColumn(name="departments_id"),
            inverseJoinColumns = @JoinColumn(name="employeemap3_id")
    )
    @MapKeyColumn(name="room")
    private Map<String, Employee> employeeMap3 = new HashMap<String, Employee>();
}
