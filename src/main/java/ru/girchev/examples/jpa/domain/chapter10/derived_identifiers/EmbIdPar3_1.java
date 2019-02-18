package ru.girchev.examples.jpa.domain.chapter10.derived_identifiers;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

/**
 * @author Girchev N.A.
 * Date: 14.02.2019
 */
@Embeddable
public class EmbIdPar3_1 implements Serializable {

    private Date dateId;
    private BigInteger bId;
}
