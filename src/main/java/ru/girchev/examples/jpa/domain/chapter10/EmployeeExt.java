package ru.girchev.examples.jpa.domain.chapter10;

import lombok.Data;
import ru.girchev.examples.jpa.domain.chapter8.AbstractEntity;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author Girchev N.A.
 * Date: 13.02.2019
 */
@Data
@Entity
@Access(AccessType.FIELD)
@Table(schema = "chapter10")
@IdClass(EmployeeId.class)
public class EmployeeExt extends AbstractEntity {

    @Id
    private String country;

    private String name;
    private BigDecimal salary;

    @Embedded
    private ContactInfoExt contactInfoExt;
}
