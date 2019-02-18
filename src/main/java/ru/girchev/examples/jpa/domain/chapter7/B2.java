package ru.girchev.examples.jpa.domain.chapter7;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Girchev N.A.
 * Date: 13.02.2019
 */
@Data
@Entity
@Table(schema = "chapter7")
public class B2 {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private A2 a2;
}
