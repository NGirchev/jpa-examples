package ru.girchev.examples.jpa.domain.chapter5.maps;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @author Girchev N.A.
 * Date: 03.03.2019
 */
@Getter
@Setter
@Embeddable
public class EmbId implements Serializable{

    private String first_id;
    private String second_id;
}
