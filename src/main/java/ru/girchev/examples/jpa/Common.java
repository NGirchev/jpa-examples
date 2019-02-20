package ru.girchev.examples.jpa;

import ru.girchev.examples.jpa.domain.Address2;
import ru.girchev.examples.jpa.domain.Realization1;
import ru.girchev.examples.jpa.domain.Realization2;
import ru.girchev.examples.jpa.domain.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * @author Girchev N.A.
 * Date: 17.02.2019
 */
public class Common {
    private EntityManagerFactory emf;
    public Common(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void test() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Test test = new Test();
        test.setId(999L);
        test.setStr("Str");
        em.persist(test);

        Realization1 realization1 = new Realization1();
        realization1.setName("Name");
        realization1.setVal("Val1");
        em.persist(realization1);

        Realization2 realization2 = new Realization2();
        realization2.setName("Name");
        realization2.setVal("Val1");
        realization2.setAddress(new Address2());
        em.persist(realization2);

        em.getTransaction().commit();
        em.close();
    }
}
