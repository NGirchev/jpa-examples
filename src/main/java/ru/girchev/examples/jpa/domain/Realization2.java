package ru.girchev.examples.jpa.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Girchev N.A.
 * Date: 18.02.2019
 */
@Data
@Entity
@DiscriminatorValue("R1")
@PrimaryKeyJoinColumn(name = "new_id")
@AssociationOverrides({ //working only with @MappedSuperclass in inheritance
        @AssociationOverride(name = "address", joinColumns = {
                @JoinColumn(name = "addrOverride")
        })
})
public class Realization2 extends AbstractInheritance2{

    String name;
}
