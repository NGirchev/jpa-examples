package ru.girchev.examples.jpa.domain.chapter4;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Girchev N.A.
 * Date: 10.02.2019
 */
@Data
@Entity
@Table(schema = "chapter4")
public class Employee2 {

    @Id
    @SequenceGenerator(name = "empl_gen_java",
            sequenceName = "empl_seq_name_db",
            schema = "chapter4")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "empl_gen_java")
    private Long id; //allocation=50, id=52 ??? But if in seq allocation will be 49, then id=50

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "building", column = @Column(name = "b"))
    })
    private Address address;
}
