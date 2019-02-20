package ru.girchev.examples.jpa.domain.chapter10.embedded_id;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * @author Girchev N.A.
 * Date: 14.02.2019
 */
@Data
@Entity
@Table(schema = "chapter10")
public class DepartmentExt3 {

    @EmbeddedId
    private DepartmentEmbId departmentEmbId;

    @OneToMany(mappedBy="departmentExt3")
    private List<ProjectExt3> projectExt3s;

//    @Embeddable
//    public class DepartmentEmbId implements Serializable {
//    @Column(name = "DEP_ID_OLD")
//    private Long depId;
//    @Column(name = "DEP_NAME_OLD")
//    private String depName;
}
