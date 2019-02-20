package ru.girchev.examples.jpa;

import ru.girchev.examples.jpa.domain.chapter11.Employee11;
import ru.girchev.examples.jpa.domain.chapter11.Employee11_;
import ru.girchev.examples.jpa.domain.chapter5.maps.Department;
import ru.girchev.examples.jpa.domain.chapter5.maps.Employee;
import ru.girchev.examples.jpa.domain.chapter5.maps.Employee_;
import ru.girchev.examples.jpa.domain.chapter8.*;
import ru.girchev.examples.jpa.domain.chapter9.CarInfo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Tuple;
import javax.persistence.criteria.*;
import java.util.ArrayList;
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

        //show the car, which owner has only one car
        System.out.println(getValues(em.createQuery(
                "select distinct c.id, c.name from Car c join c.owners co " +
                        " where 1 = (" +
                        " select co2.cars.size from CarOwner co2 " +
                        " where co2.id = co.id)"
        ).getResultList()));

        //and criteria for the same
        CriteriaQuery<Object[]> q = cb.createQuery(Object[].class);
        Root<Car> fromCar = q.from(Car.class);
        ListJoin<Car, CarOwner> joinOwners = fromCar.join(Car_.owners);

        Subquery<Long> sub = q.subquery(Long.class);
        Root<CarOwner> fromCarOwnerSub = sub.from(CarOwner.class);
        ListJoin<CarOwner, Car> joinCars = fromCarOwnerSub.join(CarOwner_.cars);
        sub.select(cb.count(joinCars)).where(cb.equal(joinOwners.get(CarOwner_.id),
                fromCarOwnerSub.get(CarOwner_.id)));
        q.multiselect(fromCar.get(Car_.id), fromCar.get(Car_.name)).distinct(true)
                .where(cb.equal(sub, 1));

        System.out.println(getValues(em.createQuery(q).getResultList()));
        // IN ('NY')
        cb.in(null).value("NY");

        CriteriaQuery<Object[]> caseQuery = cb.createQuery(Object[].class);
        Root<Car> caseFromCar = caseQuery.from(Car.class);
        caseQuery.multiselect(caseFromCar.get(Car_.name),
                cb.selectCase()
                        .when(cb.equal(caseFromCar.type(), Car.class), "CAR")
                        .when(cb.equal(caseFromCar.type(), SUV.class), "SUV")
                        .when(cb.equal(caseFromCar.type(), SportCar.class), "SPORT")
                        .otherwise("NULL"));
        System.out.println(getValues(em.createQuery(caseQuery).getResultList()));

        //NPE??? WHY???
//        CriteriaQuery<Object[]> caseQuery2 = cb.createQuery(Object[].class);
//        Root<Car> caseFromCar2 = caseQuery2.from(Car.class);
//        caseQuery2.multiselect(caseFromCar2.get(Car_.name),
//                cb.selectCase(caseFromCar2.type())
//                        .when(SUV.class, "SUV")
//                        .when(SportCar.class, "SPORT")
//                        .otherwise("CAR"));
//        System.out.println(getValues(em.createQuery(caseQuery2).getResultList()));

        System.out.println(em.createQuery(
                "select coalesce(e.name, cast(e.id as string)) from Employee11 e"
        ).getResultList());

        CriteriaQuery<String> coalesceQuery = cb.createQuery(String.class);
        Root<Employee11> coalesceFrom = coalesceQuery.from(Employee11.class);
        Path<String> stringPath = coalesceFrom.get(Employee11_.name);
        Expression<String> as = coalesceFrom.get(Employee11_.id).as(String.class);

        coalesceQuery.select(cb.coalesce(stringPath, as));

        System.out.println(em.createQuery(coalesceQuery).getResultList());

        //function initcap for first letter up
        CriteriaQuery<String> c = cb.createQuery(String.class);
        Root<Car> car = c.from(Car.class);
        c.select(cb.function("initcap", String.class, car.get("name")))
        .orderBy(cb.desc(car.get(Car_.name)));
        System.out.println(em.createQuery(c).getResultList());
        em.close();
    }

    private List<List<String>> getValues(List list) {
        List<List<String>> res = new ArrayList<>();
        for (Object obj : list) {
            List<String> strings = new ArrayList<>();
            for (Object o : (Object[]) obj){
                strings.add(o.toString());
            }
            res.add(strings);
        }
        return res;
    }
}
