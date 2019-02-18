package ru.girchev.examples.jpa.domain.chapter8;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Girchev N.A.
 * Date: 13.02.2019
 */
@Data
@MappedSuperclass
public abstract class AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @Version
    private Integer version;

}
