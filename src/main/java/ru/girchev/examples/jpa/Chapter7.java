package ru.girchev.examples.jpa;

import lombok.Data;
import org.postgresql.util.PSQLException;
import ru.girchev.examples.jpa.domain.chapter5.Cat;
import ru.girchev.examples.jpa.domain.chapter5.Person;
import ru.girchev.examples.jpa.domain.chapter5.maps.Employee;
import ru.girchev.examples.jpa.domain.chapter7.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Girchev N.A.
 * Date: 12.02.2019
 */
public class Chapter7 {

    private EntityManagerFactory emf;

    public Chapter7(EntityManagerFactory emf) {
        this.emf = emf;
    }

    /**
     * Results of Queries may be:
     * • Basic types, such as String, the primitive types, and JDBC types
     * • Entity types
     * • An array of Object
     * • User-defined types created from a constructor expression
     *
     * @throws ParseException
     */
    public void query1() throws ParseException {

        EntityManager em = emf.createEntityManager();

        String q = "select c.name from Person p, Cat c where p.id = :pId";
        System.out.println(em.createQuery(q)
                .setParameter("pId", 6L)
                .getResultList());

        q = "select c.name from Person p JOIN p.cats c";
        System.out.println(em.createQuery(q)
                .getResultList());

        q = "SELECT p, COUNT (c.id), MAX (c.id), MIN (c.id), AVG (c.id), SUM (c.id)" +
                " FROM Person p JOIN p.cats c" +
                " GROUP BY p" +
                " HAVING p.id = ?1";
        List<Object[]> resultList = em.createQuery(q, Object[].class)
                .setParameter(1, 6L)
                .getResultList();
        System.out.println(resultList.stream()
                .map(Arrays::asList)
                .collect(Collectors.toList()));

        String name = "Ivanov' OR e.name = 'Petrov";
        q = "select e from Employee e where e.name = '" + name + "'";
        System.out.println(
                em.createQuery(q)
                        .getResultList());

        System.out.println(em.createNamedQuery("Person.getCats")
                .setParameter("pId", 6L)
                .getResultList());

        System.out.println(em.createNamedQuery("getNewCat")
                .setParameter("p", em.find(Person.class, 6L))
                .setParameter("date", new SimpleDateFormat("dd.MM.yyyy")
                        .parse("01.01.2017"))
                .getResultList());

        q = "SELECT new ru.girchev.examples.jpa.domain.chapter7.Dto(" +
                "COUNT (c.id), MAX (c.id), MIN (c.id), SUM (c.id), AVG (c.id))" +
                " FROM Person p JOIN p.cats c" +
                " GROUP BY p" +
                " HAVING p.id = ?1";
        System.out.println(em.createQuery(q, Dto.class)
                .setParameter(1, 6L)
                .getResultList());

        em.getTransaction().begin();
        Employee employee = em.find(Employee.class, 10L);
        employee.setName("Killer");
        em.merge(employee);
        // CAUTION The setFirstResult() and setMaxResults() methods should not be used
        // with queries that join across collection relationships
        // (one-to-many and many-to-many) because these queries may return duplicate
        // values. The duplicate values in the result set make it impossible to use
        // a logical result position.
        q = "select distinct e.name from Department d, Employee e";
        System.out.println(em.createQuery(q)
                .setFirstResult(0)
                .setMaxResults(7) //return more then need, duplicated without distinct
                .getResultList());
        //Killer is visible before commit
        em.getTransaction().commit();

        System.out.println(em.createQuery(q)
                // timeout
                // QueryHints helps
                .setHint("javax.persistence.query.timeout", 5000)
                .setFirstResult(0)
                .setMaxResults(7)
                .getResultList());
        em.close();
    }

    /**
     * In the book:
     * bulk operation in transaction-scope preferable to execute first, but in my
     * example all good in any order!
     *
     * In extended PC will be worse:
     * Bulk operations and extended
     * persistence contexts are a particularly dangerous combination because the
     * persistence context survives across transaction boundaries, but the provider
     * will never refresh the persistence context to reflect the changed state
     * of the database after a bulk operation has completed.
     */
    public void bulkOperations() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Employee employee = em.find(Employee.class, 13L);
        employee.setName("567");
        String q = "update Employee set building = 5";
        em.createQuery(q).executeUpdate();

        em.persist(new Cat("Princess", null));
        q = "delete from Cat";
        em.createQuery(q).executeUpdate();

        em.getTransaction().rollback();
        em.close();
    }

    /**
     * org.postgresql.util.PSQLException: ERROR: update or delete on table "a"
     * violates foreign key constraint "fkf38w71a8p0x4tthayjvy35ofd" on table "b"
     *
     * specification:
     *
     * A delete operation only applies to entities of the specified
     * class and its subclasses. It does not cascade to related entities.
     */
    public void bulkOperationsCascade() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(new A(Arrays.asList(new B(), new B())));
            em.getTransaction().commit();
            em.getTransaction().begin();
            String q = "delete from A";
            em.createQuery(q).executeUpdate();
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            System.out.println("PersistenceException on bulk delete = " + e);
        } finally {
            em.close();
        }
    }

    public void cascadeWithRemoveWithOrphanRemoval() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(new A2(Arrays.asList(new B2(), new B2())));
        em.getTransaction().commit();
        em.getTransaction().begin();
        em.remove(em.find(A2.class, 29L));
        em.getTransaction().commit();
        em.close();
    }
}
