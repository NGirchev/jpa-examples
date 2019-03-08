package ru.girchev.examples.jpa.domain.chapter5.maps;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 * embedded id or as a map key must not contain such a relationship!!!!
 *
 * @author Girchev N.A.
 * Date: 03.03.2019
 */
@Data
@Embeddable
public class ItemEmbeddable {

    private String name;
    private Double price;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "addr_id")
    private ItemAddress itemAddress;
}
