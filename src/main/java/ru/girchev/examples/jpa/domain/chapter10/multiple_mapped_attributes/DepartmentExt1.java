package ru.girchev.examples.jpa.domain.chapter10.multiple_mapped_attributes;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Girchev N.A.
 * Date: 14.02.2019
 */
@Data
@Entity
@Table(schema = "chapter10")
public class DepartmentExt1 {

    @Id
    @GeneratedValue
    private Long depId;
}
