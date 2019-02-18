package ru.girchev.examples.jpa.domain.chapter10.multiple_mapped_attributes;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Girchev N.A.
 * Date: 14.02.2019
 */
@Data
@Entity
@IdClass(DeptId2.class)
@Table(schema = "chapter10")
public class DepartmentExt2 {

    @Id
    private Long number;
    @Id
    private String country;
}
