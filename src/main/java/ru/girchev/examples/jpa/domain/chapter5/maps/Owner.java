package ru.girchev.examples.jpa.domain.chapter5.maps;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

/**
 * @author Girchev N.A.
 * Date: 03.03.2019
 */
@Data
@Entity
@Table(schema = "chapter5")
public class Owner {

    @EmbeddedId
    private EmbId id;

    @MapsId("first_id")
    @ManyToOne
    private Dependent dependents;
}
