package ru.girchev.examples.jpa.domain.chapter5.maps;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

/**
 *
 * java.lang.Object
        java.lang.Throwable
            java.lang.Exception
                java.lang.RuntimeException
                    javax.persistence.PersistenceException

                        javax.persistence.NoResultException
                        javax.persistence.NonUniqueResultException
                        javax.persistence.QueryTimeoutException
                        javax.persistence.LockTimeoutException

                        javax.persistence.EntityExistsException
                        javax.persistence.EntityNotFoundException
                        javax.persistence.OptimisticLockException
                        javax.persistence.PessimisticLockException
                        javax.persistence.RollbackException
                        javax.persistence.TransactionRequiredException
 * @author Girchev N.A.
 * Date: 03.03.2019
 */
@Data
@Entity
@Table(schema = "chapter5")
public class Cart {

    @Id
    private Long id;

    //Embedded in collection
    // nor may it contain a relationship to an entity other
    // than a many-to-one or one-to-one relationship
    @ElementCollection
    @CollectionTable(schema = "chapter5", name = "cart2_embeddablemap",
            joinColumns = {@JoinColumn(name = "cart2_id")})
    @MapKeyColumn(name = "embeddableMap_key_override")
    @AttributeOverrides({ //in hibernate not working?
            @AttributeOverride(name = "name",
                    column = @Column(name = "item_name"))
    })
    @AssociationOverride( //in hibernate not working?
            name = "itemAddress",
            joinColumns = {
                    @JoinColumn(name = "addr_id_override")
            })
    private Map<String, ItemEmbeddable> embeddableMap;

    @ElementCollection
    @CollectionTable(schema = "chapter5")
    @AttributeOverrides({
            @AttributeOverride(name = "name",
                    column = @Column(name = "name_override"))
    })
    @AssociationOverride(name = "itemAddress", joinColumns = {
            @JoinColumn(name = "addr_id_override")
    })
    private List<ItemEmbeddable> itemEmbeddableList;

    //An embeddable class that is used as an embedded id or as a map
    // key must not contain such a relationship.
    @ElementCollection
    @CollectionTable(schema = "chapter5")
    @MapKeyJoinColumn(
            name = "itemstringmap_key", referencedColumnName = "fakeId")
    @Column(name = "str")
    private Map<ItemMaps, String> itemStringMap;

    @MapKey(name = "fakeId")
    @OneToMany
    @JoinTable(name = "str_item", schema = "chapter5")
    private Map<String, ItemMaps> stringItemMap;
}
