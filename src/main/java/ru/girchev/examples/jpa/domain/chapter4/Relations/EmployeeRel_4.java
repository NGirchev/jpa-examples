package ru.girchev.examples.jpa.domain.chapter4.Relations;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 Directionality: Bidirectional or Unidirectional mapping
 Cardinality: 1 -> *, * <-> *
 Ordinality: 0 -> 1 like optional

 1. Many-to-one
 2. One-to-one
 3. One-to-many
 4. Many-to-many

 Single-Valued Associations - (where the cardinality of the target is “one”
     Many-to-One Mappings
     One-to-One Mappings

 Collection-Valued Associations
    One-to-Many Mappings
    Many-to-Many Mappings

 * @author Girchev N.A.
 * Date: 10.02.2019
 */
@Data
@Entity
@Table(schema = "chapter4relations")
public class EmployeeRel_4 {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Unidirectional:
     * @ManyToOne - default creates department_id in this table.
     */
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department_4 department;

    /**
     * Unidirectional:
     * @OneToOne - default creates parkingspace_id in this table.
     * Bidirectional:
     */
    @OneToOne(optional = true
//            , mappedBy = "owner"
            , cascade = {}
            , fetch = FetchType.EAGER
            , orphanRemoval = false
            , targetEntity = void.class
    )
    @JoinColumn(unique = false
            , nullable = true
//            , columnDefinition = ""
            , insertable = true
            , updatable = true
            , table = ""
            , name =  "parkingspace_id"
//            , foreignKey = ""
//            , referencedColumnName = ""
    )
    private ParkingSpace parkingSpace;

    /**
     * creates employee_document table if mapped, instead - ass
     */
    @ManyToMany(cascade = {}
//            , mappedBy = ""
            , fetch = FetchType.LAZY
            , targetEntity = Document_4.class
//            , targetEntity = void.class
    )
    @JoinTable(schema = "chapter4relations") //I wanted example without that, but order is important
    private List<Document_4> documents = new ArrayList<Document_4>();

    @ManyToMany
    @JoinTable(name="employee_project",
            joinColumns=@JoinColumn(name="employee_id"),
            inverseJoinColumns=@JoinColumn(name="projects_id")
            , schema = "chapter4relations"
            , catalog = "randomcatalog"
//            , indexes =
//            , uniqueConstraints =
//            , foreignKey =
//            , inverseForeignKey =
    )
    private List<Project_4> projects = new ArrayList<Project_4>();
}
