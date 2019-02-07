package ru.girchev.examples.jpa;

import org.apache.commons.lang3.ArrayUtils;
import ru.girchev.examples.jpa.domain.chapter4.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestPU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        em.persist(createEmployee());

        em.getTransaction().commit();
        em.close(); emf.close();

        System.out.println("FINISHED!!!");
    }

    private static Employee createEmployee() {
        Employee e = new Employee();
        e.setName("TestName");
        e.setPhone("436346");
        e.setCharacters(ArrayUtils.toObject("Test".toCharArray()));
        e.setLongText("sdagdshsdhs");
        e.setPicture("dshsdhd".getBytes());
        e.setPic2("text".getBytes());
        return e;
    }
}
