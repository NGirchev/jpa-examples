package ru.girchev.examples.jpa.domain.chapter11;

import javax.persistence.PostPersist;

/**
 * @author Girchev N.A.
 * Date: 17.02.2019
 */
public class Listener {

    @PostPersist
    private void postPersist(Employee11 employee) {
        System.out.println("GLOBAL LISTENER +" + employee.getId());
    }
}
