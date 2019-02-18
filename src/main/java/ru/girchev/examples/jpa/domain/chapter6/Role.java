package ru.girchev.examples.jpa.domain.chapter6;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

/**
 * @author Girchev N.A.
 * Date: 12.02.2019
 */
@Data
@Entity
@ToString(exclude = "user")
@Table(schema = "chapter6")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(cascade = CascadeType.REMOVE)
    private User user;
}
