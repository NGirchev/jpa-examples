package ru.girchev.examples.jpa.domain.chapter8;

import lombok.Data;

import javax.persistence.Embeddable;
import java.math.BigDecimal;

/**
 * @author Girchev N.A.
 * Date: 13.02.2019
 */
@Data
@Embeddable
public class Specification {

    private BigDecimal speed;
}
