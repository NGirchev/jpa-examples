package ru.girchev.examples.jpa.domain.chapter10.inheritance;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Girchev N.A.
 * Date: 16.02.2019
 */
@Data
@Entity
@Table(schema = "chapter10")
@DiscriminatorValue("C@1")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class C1 extends JoinableC {

    @Id
    Long id;

    String c1;
}
