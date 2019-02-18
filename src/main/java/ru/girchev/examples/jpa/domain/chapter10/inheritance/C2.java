package ru.girchev.examples.jpa.domain.chapter10.inheritance;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Girchev N.A.
 * Date: 16.02.2019
 */
@Data
@Entity
@Table(schema = "chapter10")
public class C2 extends JoinableC {

//    @Id
    Long id;

    String c2;
}
