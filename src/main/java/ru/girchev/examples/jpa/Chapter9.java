package ru.girchev.examples.jpa;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.girchev.examples.jpa.domain.chapter8.*;
import ru.girchev.examples.jpa.domain.chapter9.CarInfo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Arrays;
import java.util.List;

/**
 *
 *  JPQL Clause         Criteria API Interface      Method
 *
    SELECT              CriteriaQuery               select()
                        Subquery                    select()

    FROM                AbstractQuery               from()
    WHERE               AbstractQuery               where()
    ORDER BY            CriteriaQuery               orderBy()

    GROUP BY            AbstractQuery               groupBy()
    HAVING              AbstractQuery               having()

 * @author Girchev N.A.
 * Date: 13.02.2019
 */
public class Chapter9 {

    private EntityManagerFactory emf;

    public Chapter9(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void init() {
        EntityManager em = emf.createEntityManager();

        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Car> q1 = cb.createQuery(Car.class);
        Root<Car> fromCar = q1.from(Car.class);
        Predicate equal = cb.equal(fromCar.get(Car_.name), "skoda");
        q1.select(fromCar).where(equal);

        System.out.println(em.createQuery(q1).getResultList());

        // select distinct c.* from car c, garage g where c.garage_id = g.id
        CriteriaQuery<Car> q2 = cb.createQuery(Car.class);
        Root<Car> fromCar2 = q2.from(Car.class);
        Root<Garage> fromG2 = q2.from(Garage.class);
        q2.select(fromCar2)
                .distinct(true)
                .where(cb.equal(fromG2, fromCar2.get(Car_.garage)));
        System.out.println(em.createQuery(q2).getResultList());

        // select c.*, c.name from car c
        CriteriaQuery<Tuple> tupleQuery = cb.createTupleQuery();
        Root<Car> fromCar3 = tupleQuery.from(Car.class);
        tupleQuery.select(cb.tuple(fromCar3.get(Car_.id).alias("id"),
                fromCar3.get(Car_.name)).alias("name2"));

        List<Tuple> resultList = em.createQuery(tupleQuery).getResultList();
        System.out.println("Tuple size: " + resultList.size());
        for (int i = 0; i < resultList.size(); i++) {
            Tuple tuple = resultList.get(i);
            System.out.println("One tuple alias elements="+tuple.getElements().get(0).getAlias()
                    +" "+tuple.getElements().get(1).getAlias());
            System.out.println("One tuple[id] element="+tuple.get("id", Long.class));
            System.out.println("One tuple[1] element="+tuple.get(1, String.class));
            List<Object> elements = Arrays.asList(tuple.toArray());
            System.out.println("TupleElement[" + i +"] size: " + elements.size());
            for (int j = 0; j < elements.size(); j++) {
                System.out.println("Obj[" + j + "]:[" + elements.get(j) +"]");
            }
        }

        CriteriaQuery<Tuple> query = cb.createTupleQuery();
//        CriteriaQuery<Object[]> query = cb.createQuery(Object[].class);
        Root<Car> from = query.from(Car.class);
        query.multiselect(from.get(Car_.id), from.get(Car_.name));
//        System.out.println(em.createQuery(query).getResultList().get(0)[0]);
        System.out.println(em.createQuery(query).getResultList().get(0).get(0));

        CriteriaQuery<CarInfo> c = cb.createQuery(CarInfo.class);
        Root<Car> path = c.from(Car.class);
        c.select(cb.construct(CarInfo.class,
                path.get("id").alias("id"),
                path.get("name")));
        System.out.println(em.createQuery(c).getResultList());
    }
}
