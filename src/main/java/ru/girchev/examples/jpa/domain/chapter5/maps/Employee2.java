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
public class Employee2 {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="F_NAME")
    private String firstName;
    @Column(name="L_NAME")
    private String lastName;

    @ManyToOne(cascade = CascadeType.ALL)
    private Department2 embeddedTestDepartment;
}
