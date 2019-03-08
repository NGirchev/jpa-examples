package ru.girchev.examples.jpa.domain.chapter5.maps;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * @author Girchev N.A.
 * Date: 03.03.2019
 */
@Data
@Entity
@Table(schema = "chapter5")
public class Dependent {

    @Id
    private String first_id;

    @OneToMany(mappedBy = "dependents")
    private List<Owner> owner;
}
