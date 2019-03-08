package ru.girchev.examples.jpa;

import ru.girchev.examples.jpa.domain.chapter10.EmployeeExt;
import ru.girchev.examples.jpa.domain.chapter10.EmployeeId;
import ru.girchev.examples.jpa.domain.chapter10.derived_identifiers.Dependent4;
import ru.girchev.examples.jpa.domain.chapter10.derived_identifiers.Parent4;
import ru.girchev.examples.jpa.domain.chapter10.embedded_id.DepartmentEmbId;
import ru.girchev.examples.jpa.domain.chapter10.embedded_id.DepartmentExt3;
import ru.girchev.examples.jpa.domain.chapter10.embedded_id.ProjectEmbId;
import ru.girchev.examples.jpa.domain.chapter10.embedded_id.ProjectExt3;
import ru.girchev.examples.jpa.domain.chapter10.inheritance.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.io.Serializable;
import java.util.Arrays;

/**
 * @author Girchev N.A.
 * Date: 13.02.2019
 */
public class Chapter10 {

    private EntityManagerFactory emf;

    public Chapter10(EntityManagerFactory emf) {
        this.emf = emf;
    }

    /**
     * ■ TIP The ability to have embeddables contain other nested embeddables, relationships to entities, and element
     * collections was introduced in JPA 2.0.
     */
    public void init() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        EmployeeExt employeeExt = new EmployeeExt();
        employeeExt.setCountry("Russia");
        employeeExt.setName("Ivanov");
        em.persist(employeeExt);

        DepartmentEmbId departmentEmbId = new DepartmentEmbId();
        departmentEmbId.setDepId(5L);
        departmentEmbId.setDepName("Department");

        ProjectEmbId projectEmbId = new ProjectEmbId();
        projectEmbId.setDepartmentEmbId(departmentEmbId);
        projectEmbId.setId(6L);

        DepartmentExt3 departmentExt3 = new DepartmentExt3();
        departmentExt3.setDepartmentEmbId(departmentEmbId);

        em.persist(departmentExt3);

        ProjectExt3 projectExt3 = new ProjectExt3();
        projectExt3.setDepartmentExt3(departmentExt3);
        projectExt3.setProjectEmbId(projectEmbId);

        departmentExt3.setProjectExt3s(Arrays.asList(projectExt3));
        em.persist(projectExt3);

        em.getTransaction().commit();
        em.clear();

        System.out.println(em.find(EmployeeExt.class, new EmployeeId(39L, "Russia")));

//        java.lang.IllegalArgumentException: Provided id of the wrong type for
//        class ru.girchev.examples.jpa.domain.chapter10.EmployeeExt. Expected:
//        class ru.girchev.examples.jpa.domain.chapter10.EmployeeId, got
//        class ru.girchev.examples.jpa.Chapter10$1Id

//        class Id implements Serializable {}
//        System.out.println(em.find(EmployeeExt.class, new Id()));

        em.close();
    }

    public void dependent() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Parent4 parent4 = new Parent4();
        em.persist(parent4);

        Dependent4 dependent4 = new Dependent4();
        dependent4.setParent4(parent4);
        em.persist(dependent4);

        em.getTransaction().commit();
        em.close();
    }

    public void inheritance() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        A a = new A();
        a.setA("a");
        em.persist(a);

        B1 b1 = new B1();
        b1.setA("a1");
        b1.setB1("b1");
        em.persist(b1);

        B2 b2 = new B2();
        b2.setA("a2");
        b2.setB2("b2");
        em.persist(b2);

        JoinableC c = new JoinableC();
        c.setC("c");
        em.persist(c);

        C1 c1 = new C1();
        c1.setC1("c1");
        c1.setC("c");
        em.persist(c1);

        C2 c2 = new C2();
        c2.setC2("c2");
        c2.setC("c");
        em.persist(c2);

        D1 d1 = new D1();
        d1.setD1("d1");
        d1.setB1("db1");
        d1.setA("da1");
        em.persist(d1);

        E1 e1 = new E1();
        e1.setE1("e1");
        e1.setC("ec");
        e1.setC1("ec1");
        em.persist(e1);

        em.getTransaction().commit();
        em.close();
    }
}
