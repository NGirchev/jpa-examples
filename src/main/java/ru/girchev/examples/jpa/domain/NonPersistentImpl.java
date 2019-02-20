package ru.girchev.examples.jpa.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author Girchev N.A.
 * Date: 19.02.2019
 */
@Entity
public class NonPersistentImpl extends NonPersistentAbstract {

    @Id
    Long id;
}
