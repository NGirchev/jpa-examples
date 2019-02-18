package ru.girchev.examples.jpa.domain.chapter10.embedded_id;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @author Girchev N.A.
 * Date: 14.02.2019
 */
@Data
@Embeddable
public class DepartmentEmbId implements Serializable {

    @Column(name = "DEP_ID_OLD")
    private Long depId;
    @Column(name = "DEP_NAME_OLD")
    private String depName;
}
