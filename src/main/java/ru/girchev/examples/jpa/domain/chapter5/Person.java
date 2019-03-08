package ru.girchev.examples.jpa.domain.chapter5;

import lombok.Data;
import org.hibernate.jpa.QueryHints;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OrderBy;
import javax.persistence.OrderColumn;
import javax.persistence.QueryHint;
import javax.persistence.Table;
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

    //ORDER COLUMN can be on @OneToMany, @ManyToMany and @ElementCollection
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
