package ru.girchev.examples.jpa.domain.chapter10.derived_identifiers;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author Girchev N.A.
 * Date: 13.02.2019
 */
@Entity
@Table(schema = "chapter10")
public class Dependent2 implements Serializable {

    @Id
    @OneToOne
    private Parent2 parent2;
}
