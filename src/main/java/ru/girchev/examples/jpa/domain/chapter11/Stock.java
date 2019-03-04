package ru.girchev.examples.jpa.domain.chapter11;

import lombok.Data;
import ru.girchev.examples.jpa.domain.chapter8.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Stock.
 *
 * @author Nikolay_Girchev
 */
@Data
@Entity
@Table(schema = "chapter11")
public class Stock extends AbstractEntity {

    String name;
    double quantity;
}
