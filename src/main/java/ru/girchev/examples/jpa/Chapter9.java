package ru.girchev.examples.jpa;

import ru.girchev.examples.jpa.domain.chapter5.maps.Employee;
import ru.girchev.examples.jpa.domain.chapter5.maps.Employee_;
import ru.girchev.examples.jpa.domain.chapter8.Car;
import ru.girchev.examples.jpa.domain.chapter8.Car_;
import ru.girchev.examples.jpa.domain.chapter8.Garage;
import ru.girchev.examples.jpa.domain.chapter9.CarInfo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Tuple;
import javax.persistence.criteria.*;
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
        next1(em, cb);
    }

    /**
     JP QL Expression       CriteriaBuilder Method

     IS EMPTY               isEmpty()
     IS NOT EMPTY           isNotEmpty()
     MEMBER OF              isMember()
     NOT MEMBER OF          isNotMember()
     LIKE                   like()
     NOT LIKE               notLike()
     IN                     in()
     NOT IN                 not(in())

     ALL                    all()
     ANY                    any()
     SOME                   some()
     -                      neg(), diff()
     +                      sum()
     *                      prod()
     /                      quot()
     COALESCE               coalesce()
     NULLIF                 nullif()
     CASE                   selectCase()

     ABS                    abs()
     CONCAT                 concat()
     CURRENT_DATE           currentDate()
     CURRENT_TIME           currentTime()
     CURRENT_TIMESTAMP      currentTimestamp()
     LENGTH                 length()
     LOCATE                 locate()
     LOWER                  lower()
     MOD                    mod()
     SIZE                   size()
     SQRT                   sqrt()
     SUBSTRING              substring()
     UPPER                  upper()
     TRIM                   trim()

     AVG                    avg()
     SUM                    sum(), sumAsLong(), sumAsDouble()
     MIN                    min(), least()
     MAX                    max(), greatest()
     COUNT                  count()
     COUNT DISTINCT         countDistinct()
     */
    private void next1(EntityManager em, CriteriaBuilder cb) {
        CriteriaQuery<Object> query = cb.createQuery();
        Root<Employee> employeeRoot = query.from(Employee.class);
        // I can use fetch like in jpql (JOIN FETCH), for eager fetching
//        employeeRoot.fetch(Employee_.phonesByType);
        // joinMap for String attribute
        MapJoin<Object, String, String> phones
                = employeeRoot.joinMap("phones", JoinType.LEFT);
        // or join with MapAttribute<Employee, String, String> metamodel
        MapJoin<Employee, String, String> mapJoin
                = employeeRoot.join(Employee_.phones, JoinType.LEFT);
        CriteriaQuery<Object> multiselect = query
                .multiselect(
                        employeeRoot.get("name"),
                        mapJoin.key(),
                        mapJoin.value()
                ).distinct(true);
        List<Object> resultList = em.createQuery(multiselect).getResultList();
        resultList.stream().forEach(o -> {
            for (Object e: (Object[]) o) {
                System.out.print(e + " ");
            }
            System.out.println();
        });

        CriteriaQuery<Employee> query2 = cb.createQuery(Employee.class);
        Root<Employee> employeeRoot2 = query2.from(Employee.class);

        Predicate predicate = cb.conjunction();
        if (true) {
            ParameterExpression<String> name =
                    cb.parameter(String.class, "nameParam");
            predicate
                    = cb.and(predicate, cb.equal(employeeRoot2.get(Employee_.name), name));
        }
        Predicate aNull = cb.isNull(employeeRoot2.get(Employee_.building));
        Expression<Integer> literal = cb.literal(1);
        Predicate t = cb.equal(employeeRoot2.get("t"), literal);

        System.out.println(em.createQuery(
                query2.select(employeeRoot2).where(cb.and(t, aNull, predicate)))
                .setParameter("nameParam", "Popov")
                .getResultList());

//        System.out.println(em.createQuery(
//                "select c from Car c where ALL c.owners"
//                + " (select co from CarOwner co" +
//                        " where count(co.cars) > 1)").getResultList());

        em.close();
    }
}
