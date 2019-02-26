package ru.girchev.examples.jpa.domain.chapter10;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;

/**
 * @author Girchev N.A.
 * Date: 13.02.2019
 */
@Entity
@Table(schema = "chapter10")
public class CustomerExt {

    @EmbeddedId
    private EmployeeId employeeId;

    /**
     * ■ TIP There is no way to override the collection table
     *      for an element collection in an embeddable.
     */
    @Embedded
    @AttributeOverride(name="addressExt.building",
            column=@Column(name="buildingOverride"))
    @AssociationOverrides({
            @AssociationOverride(
                    name="phoneExt",
                    joinColumns = @JoinColumn(name="pri_num_Override")),
            @AssociationOverride(
                    name="phoneExtMap",
                    joinTable = @JoinTable(
                            schema = "chapter10",
                            name="customer_phoneextmap"
                            , joinColumns = { //"id", "country" from EmployeeExt
                            @JoinColumn(name = "list_country_Override", referencedColumnName = "country"),
                            @JoinColumn(name = "list_id_Override", referencedColumnName = "id")
                    }
                            , inverseJoinColumns = {// "num" from PhoneExt.num
                            @JoinColumn(name = "map_num_Override", referencedColumnName = "num")
                    }
                    )
            )})
    private ContactInfoExt contactInfoExt;

    // java.lang.ExceptionInInitializerError
    // Caused by: org.hibernate.AnnotationException: A component
    // cannot hold properties split into 2 different tables:
    // ru.girchev.examples.jpa.domain.chapter10.CustomerExt.contactInfoExt
}
