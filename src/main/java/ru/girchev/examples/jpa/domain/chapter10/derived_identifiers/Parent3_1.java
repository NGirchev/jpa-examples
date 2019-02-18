package ru.girchev.examples.jpa.domain.chapter10.derived_identifiers;

import javax.persistence.*;

/**
 * @author Girchev N.A.
 * Date: 13.02.2019
 */
@Entity
@Table(schema = "chapter10")
public class Parent3_1 {

    @EmbeddedId
    private EmbIdPar3_1 id;


}
