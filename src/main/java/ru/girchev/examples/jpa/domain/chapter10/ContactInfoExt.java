package ru.girchev.examples.jpa.domain.chapter10;

import lombok.Data;
import lombok.ToString;
import ru.girchev.examples.jpa.domain.chapter5.maps.PhoneType;

import javax.persistence.*;
import java.util.Map;

/**
 * @author Girchev N.A.
 * Date: 13.02.2019
 */
@Data
@ToString(exclude = {"phoneExt", "phoneExtMap"})
@Embeddable
public class ContactInfoExt {

    @Embedded
    private AddressExt addressExt;

    @ManyToOne
    @JoinColumn(name = "pri_num", referencedColumnName = "num")
    private PhoneExt phoneExt;

    @ManyToMany
    @MapKey(name = "type")
    @JoinTable(schema = "chapter10", name = "employee_phoneExtMap"
            , joinColumns = { //"id", "country" from EmployeeExt
                    @JoinColumn(name = "list_country", referencedColumnName = "country"),
                    @JoinColumn(name = "list_id", referencedColumnName = "id")
            }
            , inverseJoinColumns = {// "num" from PhoneExt.num
                    @JoinColumn(name = "map_num", referencedColumnName = "num")
            }
    )
    private Map<PhoneType, PhoneExt> phoneExtMap;
}
