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
@ToString(exclude = "role")
@Table(schema = "chapter6")
public class User {

    @Id
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToOne(mappedBy = "user")
    private Role role;

    @OneToOne
    private Permission permission;

}
