package ru.girchev.examples.jpa;

import ru.girchev.examples.jpa.domain.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestPU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Employee e = new Employee();
        e.setName("TestName");
        em.persist(e);
        em.getTransaction().commit();
        em.close();
        emf.close();
        System.out.println("Hello World!");
    }
}
