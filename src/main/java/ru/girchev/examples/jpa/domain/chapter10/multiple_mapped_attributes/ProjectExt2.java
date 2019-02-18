package ru.girchev.examples.jpa.domain.chapter10.multiple_mapped_attributes;

import javax.persistence.*;

/**
 *
 *
 * @author Girchev N.A.
 * Date: 14.02.2019
 */
@Entity
@Table(schema = "chapter10")
@IdClass(ProjectId2.class)
public class ProjectExt2 {

// ProjectId2.class:
//    private DeptId2 dept2;
//    private String name;

// DeptId2.class:
//    private Long number;
//    private String country;

    @Id
    private String name;            // name equals @IdClass(ProjectId2.class)

    @Id
    @ManyToOne
    private DepartmentExt2 dept2;   // name equals @IdClass(ProjectId2.class)
}
