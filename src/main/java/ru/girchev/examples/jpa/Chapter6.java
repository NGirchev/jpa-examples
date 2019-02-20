package ru.girchev.examples.jpa;

import ru.girchev.examples.jpa.domain.chapter4.Relations.EmployeeRel_4;
import ru.girchev.examples.jpa.domain.chapter6.Role;
import ru.girchev.examples.jpa.domain.chapter6.User_6;

import javax.persistence.*;

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

        EmployeeRel_4 e = new EmployeeRel_4();
        em.getTransaction().begin();
        em.persist(e);
        // IF I flush without opened transaction..
        //javax.persistence.TransactionRequiredException: no transaction is in progress
        em.flush();
        em.getTransaction().commit();

        EntityManager em2 = emf.createEntityManager();

        System.out.println("AFTER TRANSACTION ENTITY IS IN PC1 TRUE=" + em.contains(e));
        System.out.println("In Cache="+emf.getCache().contains(EmployeeRel_4.class, e.getId()));

        System.out.println("AFTER TRANSACTION ENTITY IS IN PC2 FALSE=" + em2.contains(e));

        em.close();
        em2.close();
    }

    /**
     * without JTA joinTransaction not working
     * But in standalone PersistenceContext shares between transactions within one EntityManager
     */
    public void joinTransaction() {
        EntityManager em = emf.createEntityManager();
        System.out.println("IS JOINED FALSE=" + em.isJoinedToTransaction());
        //WARN: HHH000027: Calling joinTransaction() on a non JTA EntityManager
        em.joinTransaction();
        System.out.println("AFTER JOIN FALSE=" + em.isJoinedToTransaction());
        em.close();
    }

    /**
     * Exception in thread "main" javax.persistence.EntityExistsException
     */
    public void testRollback() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        User_6 u = new User_6();
        u.setId(123L);
        u.setName("");
        em.persist(u);
        em.getTransaction().commit();
        try {
            em.getTransaction().begin();
            User_6 u2 = new User_6();
            u2.setId(123L);
            u2.setName("");
            em.persist(u2);
//        System.out.println(em.getTransaction().getRollbackOnly()); //false
            if(em.getTransaction().getRollbackOnly())
                em.getTransaction().rollback();
            else
                em.getTransaction().commit();
        } catch (EntityExistsException e) {
            System.out.println("EntityExistsException RolledBack = " + em.getTransaction().getRollbackOnly());
        } finally {
            em.close();
        }
    }

    /**
     * The getReference() call is a performance optimization that should be
     * used only when there is evidence to suggest that it will actually
     * benefit the application
     */
    public void referenceFind() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        User_6 u = em.getReference(User_6.class, 123L);
        Role r = new Role();
        r.setUser(u);
        em.persist(r);
        em.getTransaction().commit();
        try {
            u = em.getReference(User_6.class, 124L);
            u.setName("dsgds");
        } catch (EntityNotFoundException e) {
            System.out.println("EntityNotFoundException expected = " + e.getClass());
        }
        try {
            em.getTransaction().begin();
            u = em.getReference(User_6.class, 124L);
            r = new Role();
            r.setUser(u);
            em.persist(r);
            em.getTransaction().commit();
        } catch (RollbackException e) {
            System.out.println("RollbackException expected = " + e.getClass());
        } finally {
            em.close();
        }
    }

    public void cascadeRemove() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        User_6 user = em.find(User_6.class, 123L);
        em.remove(em.find(Role.class, 22L));
        em.getTransaction().commit();
        System.out.println("User not in context = " + em.contains(user));
        System.out.println("User in-memory have role = " + user.getRole());
        em.close();
    }

    public void oneToOnePersistDetached() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        User_6 user = new User_6();
        user.setId(124L);
        user.setName("Name");
        // In the book written - it's legal, without any exceptions
        // In fact - IllegalStateException: TransientPropertyValueException
//        user.setPermission(new Permission());
        em.persist(user);
        em.getTransaction().commit();
        em.close();
    }

    public void merge() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        User_6 user = new User_6();
        user.setId(124L);
        user.setName("NewName");
        em.merge(user);

        User_6 user2 = new User_6();
        user2.setId(125L);
        user2.setName("Name");
        em.merge(user2);

        Role r = new Role();
        em.merge(r);
        em.getTransaction().commit();
        em.close();
    }
}
