package ru.girchev.examples.jpa.domain.chapter4.Relations;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Girchev N.A.
 * Date: 10.02.2019
 */
@Data
@Entity
@Table(schema = "chapter4relations")
public class ParkingSpace {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(mappedBy = "parkingSpace")
    private EmployeeRel_4 owner;
}
