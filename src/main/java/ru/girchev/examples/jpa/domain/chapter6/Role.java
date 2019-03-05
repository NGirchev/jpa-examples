package ru.girchev.examples.jpa.domain.chapter6;

import lombok.Data;
import lombok.ToString;

import javax.annotation.PreDestroy;
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
    private User_6 user;

    private String name;

    @PrePersist
    private void prePersist() {
        System.out.println("prePersist " + id);
    }
    @PostPersist
    private void postPersist() {
        System.out.println("postPersist " + id);
    }
    @PreUpdate
    private void preUpdate() {
        System.out.println("preUpdate " + id);
    }
    @PostUpdate
    private void postUpdate() {
        System.out.println("postUpdate " + id);
    }
    @PreRemove
    private void preRemove() {
        System.out.println("preRemove " + id);
    }

    @PostRemove
    private void postRemove() {
        System.out.println("postRemove " + id);
    }

    @PostLoad
    private void postLoad() {
        System.out.println("postLoad " + id);
    }
}