package ru.girchev.examples.jpa.domain.chapter5;

import lombok.Data;
import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortNatural;
import org.hibernate.jpa.QueryHints;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Girchev N.A.
 * Date: 10.02.2019
 */
@Data
@Entity
@Table(schema = "chapter5")
@NamedQueries({
        @NamedQuery(name = "Person.getCats",
                query = "select c.name from " +
                        "Person p, Cat c " +
                        "where p.id = :pId",
                hints = {
                        @QueryHint(name = QueryHints.HINT_COMMENT, value = "Test Comment")
                }),
        @NamedQuery(name = "getNewCat",
                query = "select c.name from " +
                        "Person p, Cat c " +
                        "where p = :p and c.birthDate > :date")
})
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ElementCollection(
            targetClass = String.class,
            fetch = FetchType.LAZY)
    @CollectionTable(name = "person_phones"
            , schema = "chapter5"
            , catalog = "randomcatalog"
            , joinColumns = @JoinColumn(name = "person_id")
//            , foreignKey =
//            , uniqueConstraints =
//            , indexes =
    )
    @Column(name = "phones")
    @OrderBy("phones asc")
    List<String> phones;

    /**
     java.lang.ExceptionInInitializerError
     Caused by: org.hibernate.AnnotationException: Collection has neither
     generic type or OneToMany.targetEntity() defined:
     ru.girchev.examples.jpa.domain.chapter5.Person.nickNames
     */
    @ElementCollection
    @CollectionTable(name = "person_items"
            , schema = "chapter5"
            , catalog = "randomcatalog"
            , joinColumns = @JoinColumn(name = "person_id")
    )
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "name2")),
            @AttributeOverride(name = "description", column = @Column(name = "description2"))
    })
    @OrderBy("phone.model asc")
    List<Item> items;

    @ElementCollection
    @CollectionTable(schema = "chapter5")
    Set<String> nickNames;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(schema = "chapter5")
    @OrderColumn(name = "order_col"
            , updatable = true
            , insertable = true
            , nullable = true
//            , columnDefinition =
    )
    private List<Cat> cats = new ArrayList<Cat>();
}
