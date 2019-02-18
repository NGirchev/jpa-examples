package ru.girchev.examples.jpa.domain.chapter12;

import lombok.Data;

import javax.persistence.MappedSuperclass;

/**
 * @author Girchev N.A.
 * Date: 17.02.2019
 */
@Data
public abstract class Abstr {

    Integer version;
}
