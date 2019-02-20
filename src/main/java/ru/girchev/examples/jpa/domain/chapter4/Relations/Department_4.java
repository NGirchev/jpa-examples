package ru.girchev.examples.jpa.domain.chapter4.Relations;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Girchev N.A.
 * Date: 10.02.2019
 */
@Data
@Entity
@Table(schema = "chapter4relations")
public class Department_4 {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "department"
            , targetEntity = EmployeeRel_4.class
            , orphanRemoval = false
            , fetch = FetchType.LAZY
            , cascade = {}
    )
    private Collection employees = new ArrayList();

    private String name;
}
