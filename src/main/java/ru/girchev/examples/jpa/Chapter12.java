package ru.girchev.examples.jpa;

import ru.girchev.examples.jpa.domain.chapter12.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * @author Girchev N.A.
 * Date: 17.02.2019
 */
public class Chapter12 {
    private EntityManagerFactory emf;
    public Chapter12(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void test() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Employee e = new Employee();
        e.setName("Name");
        em.persist(e);

        em.getTransaction().commit();
        em.close();
    }
}
