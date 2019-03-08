package ru.girchev.examples.jpa.domain.chapter10.multiple_mapped_attributes;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * • The primary key class must be serializable.
 • The primary key class must define equals and hashCode methods. The semantics of value equality for
 these methods must be consistent with the database equality for the database types to which the key is mapped.
 * @author Girchev N.A.
 * Date: 14.02.2019
 */
@Getter
@Setter
public class DeptId2 implements Serializable {

    private Long number;
    private String country;
}
