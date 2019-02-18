package ru.girchev.examples.jpa.domain.chapter10.embedded_id;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Girchev N.A.
 * Date: 14.02.2019
 */
@Data
@Entity
@Table(schema = "chapter10")
public class ProjectExt3 {

    @EmbeddedId
    private ProjectEmbId projectEmbId;

    @MapsId(value = "departmentEmbId") // maps to ProjectExt3.projectEmbId.departmentEmbId;
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "DEP_ID_NEW", referencedColumnName = "DEP_ID_OLD"),
            @JoinColumn(name = "DEP_NAME_NEW", referencedColumnName = "DEP_NAME_OLD")
    })
    private DepartmentExt3 departmentExt3;

//    @Embeddable
//    public class ProjectEmbId implements Serializable {
//        private Long id;
//        @Embedded
//        private DepartmentEmbId departmentEmbId;

//    @Embeddable
//    public class DepartmentEmbId implements Serializable {
//        private Long depId;
//        private String depName;
}
