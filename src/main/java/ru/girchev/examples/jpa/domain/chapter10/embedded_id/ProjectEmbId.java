package ru.girchev.examples.jpa.domain.chapter10.embedded_id;

import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.io.Serializable;

/**
 * @author Girchev N.A.
 * Date: 14.02.2019
 */
@Data
@Embeddable
public class ProjectEmbId implements Serializable {

    private Long id;
    @Embedded
    private DepartmentEmbId departmentEmbId;
}
