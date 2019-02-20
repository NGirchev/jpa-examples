package ru.girchev.examples.jpa.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Girchev N.A.
 * Date: 18.02.2019
 */
@Data
//@Entity
@MappedSuperclass
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class AbstractInheritance2 {
    @Id @GeneratedValue Long id;
    private String val;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="ADDR")
    protected Address2 address;
}
