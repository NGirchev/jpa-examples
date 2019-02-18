package ru.girchev.examples.jpa;

import org.apache.commons.lang3.ArrayUtils;
import ru.girchev.examples.jpa.domain.chapter4.*;
import ru.girchev.examples.jpa.domain.chapter4.Relations.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * @author Girchev N.A.
 * Date: 10.02.2019
 */
public class Chapter4 {

    private EntityManagerFactory emf;

    public Chapter4(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void makeIds() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Employee1 employee1 = createEmployee();
        em.persist(employee1);
        em.flush();

        System.out.println("CONTAINS IN CACHE = " +
                em.getEntityManagerFactory()
                        .getCache().contains(Employee1.class, employee1.getId()));

        Employee2 employee2 = new Employee2();
        em.persist(employee2);
        // Without @GeneratedValue:
        // IdentifierGenerationException: ids for this class must be manually
        // assigned before calling save()
        Id1String id1String = new Id1String();
        em.persist(id1String);
        em.persist(new Id2BigInteger());
//        em.persist(new Id3Byte()); //IllegalArgumentException: Unknown entity: ru.girchev.examples.jpa.domain.chapter4.Id3Byte

        System.out.println("SEQUENCE ID expect 1 = " + employee1.getId());
        System.out.println("IDENTITY ID expect 1 = " + id1String.getId()); // but, it is not accurate, before commin

        Address address = new Address();
        address.setBuilding("1");
        address.setStreet("Lenina");
        employee1.setAddress(address);

        em.getTransaction().commit();
        em.getTransaction().begin();

        employee1.setName("Ivanov"); //changes in employee1 in second transaction
        address.setBuilding("2");
        employee2.setAddress(address);

        em.getTransaction().commit();
        em.close();
    }

    public void makeRelations() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Employee employee = new Employee();

        Department department = new Department();
        department.setName("TestName");
        department.getEmployees().add(employee);

        ParkingSpace parkingSpace = new ParkingSpace();

        Document document = new Document();
        document.getEmployees().add(employee);

        Project project = new Project();
        project.getEmployees().add(employee);

        employee.setDepartment(department);
        employee.setParkingSpace(parkingSpace);
        employee.getDocuments().add(document);
        employee.getProjects().add(project);

        em.persist(parkingSpace);
        em.persist(department);
        em.persist(document);
        em.persist(project);
        em.persist(employee);

        em.getTransaction().commit();

        if(em.find(Document.class, document.getId()).getEmployees().size() != 1)
            throw new RuntimeException();
        em.close();
    }

    private static Employee1 createEmployee() {
        Employee1 e = new Employee1();
        e.setName("TestName");
        e.setPhone("436346");
        e.setCharacters(ArrayUtils.toObject("Test".toCharArray()));
        e.setLongText("sdagdshsdhs");
        e.setPicture("dshsdhd".getBytes());
        e.setPic2("text".getBytes());
        e.setLastname("123456789");
        e.setColDefinitionTest("test");
        return e;
    }
}
