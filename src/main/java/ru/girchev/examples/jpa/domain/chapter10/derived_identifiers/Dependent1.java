package ru.girchev.examples.jpa.domain.chapter10.derived_identifiers;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;

/**
 *
 *
 • A dependent entity might have multiple parent entities, i.e., a derived identifier
 might include multiple foreign keys.

 • A dependent entity must have all its relationships to parent entities set before it
 can be persisted.

 • If an entity class has multiple id attributes, then not only must it use an id class,
 but there must also be a corresponding attribute of the same name in the id class
 as each of the id attributes in the entity.

 • Id attributes in an entity might be of a simple type, or of an entity type that is the
 target of a many-to-one or one-to-one relationship.

 • If an id attribute in an entity is of a simple type, then the type of the matching
 attribute in the id class must be of the same simple type.

 • If an id attribute in an entity is a relationship, then the type of the matching
 attribute in the id class is of the same type as the primary key type of the target
 entity in the relationship (whether the primary key type is a simple type, an id
 class, or an embedded id class).

 • If the derived identifier of a dependent entity is in the form of an embedded id
 class, then each attribute of that id class that represents a relationship should be
 referred to by a @MapsId annotation on the corresponding relationship attribute.

 * @author Girchev N.A.
 * Date: 13.02.2019
 */
@Entity
@Table(schema = "chapter10")
public class Dependent1 implements Serializable {

    @Id
    @OneToOne
    private Parent1 parent1;
}
