package ru.girchev.examples.jpa;

import ru.girchev.examples.jpa.domain.chapter4.Relations.Department;
import ru.girchev.examples.jpa.domain.chapter4.Relations.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

/**
 *
 * An application-managed entity manager participates in a JTA transaction in one of two ways.
 *      If the persistence context is created inside the transaction, the persistence provider
 * will automatically synchronize the persistence context with the transaction.
 *      If the persistence context was created earlier (outside of a transaction or in a
 * transaction that has since ended), the persistence context can be manually
 * synchronized with the transaction by calling joinTransaction() on the EntityManager
 * interface. Once synchronized, the persistence context will automatically be flushed
 * when the transaction commits.
 *
 * @author Girchev N.A.
 * Date: 11.02.2019
 */
public class Chapter6 {

    private EntityManagerFactory emf;

    public Chapter6(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void testContext() {
        EntityManager em = emf.createEntityManager();
        System.out.println(em.getProperties());

        Employee e = new Employee();
        em.getTransaction().begin();
        em.persist(e);
        // IF I flush without opened transaction..
        //javax.persistence.TransactionRequiredException: no transaction is in progress
        em.flush();
        em.getTransaction().commit();

        EntityManager em2 = emf.createEntityManager();

        System.out.println("AFTER TRANSACTION ENTITY IS IN PC1=" + em.contains(e));
        System.out.println("In Cache="+emf.getCache().contains(Employee.class, e.getId()));

        System.out.println("AFTER TRANSACTION ENTITY IS IN PC2=" + em2.contains(e));

        em.close();
        em2.close();
    }

    /**
     * without JTA joinTransaction not working
     */
    public void joinTransaction() {
        EntityManager em = emf.createEntityManager();
        System.out.println("IS JOINED="+em.isJoinedToTransaction());
        //WARN: HHH000027: Calling joinTransaction() on a non JTA EntityManager
        em.joinTransaction();
        System.out.println("AFTER JOIN="+em.isJoinedToTransaction());
        em.close();
    }

}
