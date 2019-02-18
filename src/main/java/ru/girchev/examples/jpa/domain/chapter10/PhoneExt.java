package ru.girchev.examples.jpa.domain.chapter10;

import lombok.Data;
import ru.girchev.examples.jpa.domain.chapter5.maps.PhoneType;
import ru.girchev.examples.jpa.domain.chapter8.AbstractEntity;

import javax.persistence.*;
import java.util.List;

/**
 * @author Girchev N.A.
 * Date: 13.02.2019
 */
@Data
@Entity
@Table(schema = "chapter10")
public class PhoneExt {

    @Id
    private String num;

    @ManyToMany(mappedBy = "contactInfoExt.phoneExtMap")
    private List<EmployeeExt> employeeExtList;

    @Enumerated(value = EnumType.STRING)
    private PhoneType type;
}
