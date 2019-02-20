package ru.girchev.examples.jpa;

import ru.girchev.examples.jpa.domain.chapter8.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Girchev N.A.
 * Date: 13.02.2019
 */
public class Chapter8 {

    private EntityManagerFactory emf;

    public Chapter8(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void polymorphAndInheritance() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Garage garage = new Garage();
        em.persist(garage);

        CarOwner ivanov = new CarOwner();
        ivanov.setName("Ivanov");
        em.persist(ivanov);

        CarOwner petrov = new CarOwner();
        petrov.setName("Petrov");
        em.persist(petrov);

        CarOwner sidorov = new CarOwner();
        sidorov.setName("Sidorov");
        em.persist(sidorov);

        Specification s = new Specification();

        Car skoda = new Car();
        skoda.setName("skoda");
        s.setSpeed(new BigDecimal(200.0));
        skoda.setSpecification(s);
        skoda.setGarage(garage);
        ivanov.getCars().add(skoda);
        sidorov.getCars().add(skoda);
        em.persist(skoda);

        SportCar bugatti = new SportCar();
        bugatti.setName("bugatti");
        s = new Specification();
        s.setSpeed(new BigDecimal(800.0));
        bugatti.setSpecification(s);
        bugatti.setRaceCar(true);
        bugatti.setGarage(garage);
        ivanov.getCars().add(bugatti);
        petrov.getCars().add(bugatti);
        em.persist(bugatti);

        SUV jeep = new SUV();
        jeep.setName("jeep");
        s = new Specification();
        s.setSpeed(new BigDecimal(150.0));
        jeep.setSpecification(s);
        jeep.setAllWeelDrive(true);
        jeep.setGarage(garage);
        petrov.getCars().add(jeep);
        em.persist(jeep);

        // [INNER] JOIN as DEFAULT like in SQL
        String q = "select c.specification.speed from Garage g " +
                "FULL JOIN g.cars c where TYPE(c) = SportCar or TYPE(c) = Car";
        System.out.println(em.createQuery(q).getResultList());

        // JOIN USING WITHOUT KEYWORD JOIN
        q = "select sc.garage.id from SportCar sc where sc.garage.id is not null";
        System.out.println(em.createQuery(q).getResultList());

        // IN versus JOIN
        q = "select distinct c.name from Garage g, IN (g.cars) c" +
                " where c.owners IS NOT EMPTY";
        System.out.println(em.createQuery(q).getResultList());

        q = "select o.name from CarOwner o JOIN o.cars c" +
                " group by o" +
                " having count(c) = 2";
        System.out.println(em.createQuery(q).getResultList());

        //enums
        Object[] types = em.createNamedQuery("Employee1.getEmployeeType",
                Object[].class).getResultList().get(0);
        System.out.println(types[0] + " " + types[1]);

        q = "select p from Employee e JOIN e.phones p";
        System.out.println(em.createQuery(q).getResultList());

        // TIP
        // The KEY, VALUE, and ENTRY keywords for map operations were introduced
        // in JPA 2.0. In JPA 1.0, Maps could only contain entities, and a path
        // expression that resolved to a Map always referred to the entity
        // values of the Map.
        q = "select e.name, key(p), value(p) from Employee e JOIN e.phones p";
        List resultList = em.createQuery(q, Object[].class)
                .getResultList().stream()
                .map(Arrays::asList)
                .collect(Collectors.toList());
        System.out.println(resultList);


        // ■ TIP Support for Java enum literals, entity type literals and temporal literals was added in JPA 2.0.
        String query;
        query = "select e from " +
                "Employee e where key(e.phonesByType) " +
                "= ru.girchev.examples.jpa.domain.chapter5.maps.PhoneType.HOME";
        System.out.println(em.createQuery(query));

        // 12.01.2015
        // Hibernate generated grammar does not include support for temporal literals.
        // The JPA specification does include support for temporal literals but does not
        // require persistence providers to translate from from the JPA syntax to the
        // native syntax of the JDBC driver. From the JPA2 Spec 4.6.1:
        // "The JDBC escape syntax may be used for the specification of date, time,
        // and timestamp literals."
        //
        // The functionality lacking here as far as my needs are concerned is that you
        // cannot do a select coalesce(somePath, someDateLiteral) query. You can still
        // do a where somePath=someDate. As long as they're mapped entities you can
        // throw whatever you want in a where clause.
        query = "select c from " +
                "Cat c where c.birthDate > {d '2015-01-12'}";
//        System.out.println(em.createQuery(query));

        em.getTransaction().commit();
        em.close();
    }
}
