package ru.girchev.examples.jpa.domain.chapter4.Relations;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Girchev N.A.
 * Date: 10.02.2019
 */
@Data
@Entity
@Table(schema = "chapter4relations")
public class Project_4 {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToMany(mappedBy = "projects")
    private List<EmployeeRel_4> employees = new ArrayList<EmployeeRel_4>();
}
