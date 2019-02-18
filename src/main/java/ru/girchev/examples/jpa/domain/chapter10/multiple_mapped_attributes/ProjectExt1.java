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
@IdClass(ProjectId1.class)
public class ProjectExt1 {

    @Id
    private String name;            // name equals @IdClass(ProjectId1.class)

    @Id
    @ManyToOne
    private DepartmentExt1 dept1;   // name equals @IdClass(ProjectId1.class)
}
