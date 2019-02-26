package ru.girchev.examples.jpa.domain.chapter9;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * NewEntity.
 *
 * @author Nikolay_Girchev
 */
@Data
@Entity
public class NewEntity {
    @Id
    Long id;
}
