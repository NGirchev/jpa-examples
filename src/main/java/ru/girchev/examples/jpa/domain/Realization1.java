package ru.girchev.examples.jpa.domain;

import lombok.Data;
import ru.girchev.examples.jpa.domain.AbstractInheritance;

import javax.persistence.*;

/**
 * @author Girchev N.A.
 * Date: 18.02.2019
 */
@Data
@Entity
@DiscriminatorValue("R1")
@PrimaryKeyJoinColumn(name = "new_id")
@AssociationOverrides({
        @AssociationOverride(name = "address", joinColumns = {
                @JoinColumn(name = "addrOverride")
        })
})
public class Realization1 extends AbstractInheritance{

    String name;
}
