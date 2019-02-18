package ru.girchev.examples.jpa.domain.chapter10.derived_identifiers;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Girchev N.A.
 * Date: 13.02.2019
 */
@Data
@Entity
@Table(schema = "chapter10")
public class Dependent4 implements Serializable {

    @Id
    private Long depId;

    @MapsId
    @OneToOne
    @JoinColumn(name = "testName")
    private Parent4 parent4;
}
