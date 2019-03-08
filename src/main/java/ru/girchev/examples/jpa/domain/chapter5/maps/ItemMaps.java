package ru.girchev.examples.jpa.domain.chapter5.maps;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Girchev N.A.
 * Date: 03.03.2019
 */
@Data
@Entity
@Table(schema = "chapter5")
public class ItemMaps {

    @Id
    private Long id;

    private String fakeId;
}
