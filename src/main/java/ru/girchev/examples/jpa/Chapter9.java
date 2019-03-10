package ru.girchev.examples.jpa;

import ru.girchev.examples.jpa.domain.chapter11.Employee11;
import ru.girchev.examples.jpa.domain.chapter11.Employee11_;
import ru.girchev.examples.jpa.domain.chapter5.maps.Department;
import ru.girchev.examples.jpa.domain.chapter5.maps.Employee;
import ru.girchev.examples.jpa.domain.chapter5.maps.Employee_;
import ru.girchev.examples.jpa.domain.chapter8.*;
import ru.girchev.examples.jpa.domain.chapter9.CarInfo;
import ru.girchev.examples.jpa.domain.chapter9.NewEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Tuple;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.EmbeddableType;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.Metamodel;
import javax.persistence.metamodel.SingularAttribute;
import java.security.acl.Owner;
import java.util.AbstractQueue;
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
//        fromCar.fetch()
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
                fromCar3.get(Car_.name).alias("name2"),
                fromCar3.get(Car_.garage).get(Garage_.id).alias("garageId")));

        List<Tuple> resultList = em.createQuery(tupleQuery).getResultList();
        System.out.println("Tuple size: " + resultList.size());
        for (int i = 0; i < resultList.size(); i++) {
            Tuple tuple = resultList.get(i);
            System.out.println("One tuple alias elements="+tuple.getElements().get(0).getAlias()
                    +" "+tuple.getElements().get(1).getAlias()+" "+tuple.getElements().get(2).getAlias());
            System.out.println("One tuple[id] element="+tuple.get("id", Long.class));
            System.out.println("One tuple[1] element="+tuple.get(1, String.class));
            System.out.println("One tuple[garageId] element="+tuple.get("garageId", Long.class));
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
        next2(em, cb);
        next3(em, cb);
        em.close();
    }

    /**
     JPQL Expression        CriteriaBuilder Method

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

         // return customers who have changed their last name
         select nullif( c.previousName.last, c.name.last )
         from Customer c

         // equivalent CASE expression
         select case when c.previousName.last = c.name.last then null
         else c.previousName.last end
         from Customer c

     CASE                   selectCase()
        select case c.nickName when null then '<no nick name>' else c.nickName end from Customer c

     ABS                    abs()
     CONCAT                 concat()
     CURRENT_DATE           currentDate()
     CURRENT_TIME           currentTime()
     CURRENT_TIMESTAMP      currentTimestamp()
     LENGTH                 length()
     LOCATE                 locate()

         The LOCATE(str, substr [, start]) function searches a substring and returns its position.
         For example:
         LOCATE('India', 'a') is evaluated to 5.

     LOWER                  lower()
     MOD                    mod()
        MOD(11, 3) is evaluated to 2 (3 goes into 11 three times with a remainder of 2)
       
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
    }

    private void next2(EntityManager em, CriteriaBuilder cb) {
        Metamodel metamodel = em.getMetamodel();
        EntityType<Car> car = metamodel.entity(Car.class);
        System.out.println(car.getAttributes());
        EntityType<NewEntity> newEntity = metamodel.entity(NewEntity.class);
        System.out.println(newEntity.getAttributes());
        ManagedType<Car> carManagedType = metamodel.managedType(Car.class);
        System.out.println(carManagedType.getAttributes());

        CriteriaQuery<Object> query2 = cb.createQuery();
        Root<Employee> employeeRoot2 = query2.from(Employee.class);
        EntityType<Employee> empModel2 = employeeRoot2.getModel();
        MapJoin<Employee, ?, ?> mapJoin2
                = employeeRoot2.join(empModel2.getMap("phones"), JoinType.INNER);
        CriteriaQuery<Object> multiselect2 = query2
                .multiselect(
                        employeeRoot2.get("name"),
                        mapJoin2.key(),
                        mapJoin2.value()
                ).distinct(true);
        List<Object> resultList2 = em.createQuery(multiselect2).getResultList();
        resultList2.stream().forEach(o -> {
            for (Object e: (Object[]) o) {
                System.out.print(e + " ");
            }
            System.out.println();
        });

        CriteriaQuery<Car> query = cb.createQuery(Car.class);
        Root<Car> from = query.from(Car.class);
        EntityType<Car> model = from.getModel();
        SingularAttribute<? super Car, String> name = model.getSingularAttribute("name", String.class);
        query.select(from).where(cb.equal(from.get(name), "skoda"));
        System.out.println(em.createQuery(query).getResultList());
    }

    private List<List<String>> getValues(List list) {
        List<List<String>> res = new ArrayList<>();
        for (Object obj : list) {
            List<String> strings = new ArrayList<>();
            for (Object o : (Object[]) obj) {
                strings.add(o.toString());
            }
            res.add(strings);
        }
        return res;
    }

    private void next3(EntityManager em, CriteriaBuilder cb) {
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<Car> from = ((AbstractQuery)query).from(Car.class);
        ((AbstractQuery)query.select(from.get(Car_.id)))
             .groupBy(from.get(Car_.id));
        System.out.println(em.createQuery(query).getResultList());

        CriteriaQuery<Tuple> query2 = cb.createTupleQuery();
        Root<Car> from2 = query2.from(Car.class);
        query2.multiselect(from2.get(Car_.id), from2.get(Car_.name));
        System.out.println(em.createQuery(query2).getResultList().get(0).get(1));

        CriteriaQuery<Garage> query3 = cb.createQuery(Garage.class);
        Root<Car> from3 = query3.from(Car.class);
        Join<Car, Garage> join = from3.join(Car_.garage, JoinType.LEFT);
        query3.select(join);
        System.out.println(em.createQuery(query3).getResultList().size());

        CriteriaQuery<Car> query4 = cb.createQuery(Car.class);
        Root<Car> from4 = query4.from(Car.class);
        from4.fetch(Car_.owners, JoinType.LEFT);
        query4.select(from4).distinct(true);
        System.out.println(em.createQuery(query4).getResultList().size());

        CriteriaQuery<Garage> query5 = cb.createQuery(Garage.class);
        Root<Car> from5 = query5.from(Car.class);
        Root<Garage> from5Garage = query5.from(Garage.class);
        query5.select(from5Garage).where(cb.equal(from5.get(Car_.name), "skoda"));
        System.out.println(em.createQuery(query5).getResultList().size());

//        выбрать всех владельцев машин, у которых машины в каком-то гараже
//        select * from chapter8.carowner co
//        join chapter8.carowner_car coc on coc.owners_id = co.id
//        join chapter8.car c on c.id = coc.cars_id
//        WHERE c.garage_id in (SELECT g.id FROM chapter8.garage g);

        // Subqueries may be used in the WHERE or HAVING clause.
        CriteriaQuery<CarOwner> query6 = cb.createQuery(CarOwner.class);
        Root<CarOwner> from6 = query6.from(CarOwner.class);
        ListJoin<CarOwner, Car> join1 = from6.join(CarOwner_.cars);
        Subquery<Garage> subquery = query6.subquery(Garage.class);
        subquery.select(subquery.from(Garage.class));
        query6.select(from6).where(join1.get(Car_.garage).in(subquery));
        System.out.println(em.createQuery(query6.distinct(true)).getResultList().size());


        CriteriaQuery<CarOwner> query7 = cb.createQuery(CarOwner.class);
        Root<CarOwner> ownerRoot = query7.from(CarOwner.class);
        Subquery<Long> subquery1 = query7.subquery(Long.class);
        Root<CarOwner> ownerRoot2 = subquery1.correlate(ownerRoot);
        subquery1.select(cb.count(ownerRoot2.join(CarOwner_.cars)));
        Predicate predicate = cb.greaterThanOrEqualTo(subquery1, 2L);
        query7.select(ownerRoot).where(predicate);
        System.out.println(em.createQuery(query7).getResultList().size());

        String q = "select count(distinct e) from Department d, Employee e";
        System.out.println("distinct count: "+em.createQuery(q)
                .getResultList()); //4

        //not working
        q = "select distinct count(e) from Department d, Employee e";
        System.out.println("distinct count: "+em.createQuery(q)
                .getResultList()); //8

        //Only identification variables and path expressions are currently supported in
        // the GROUP BY clause by all the JPA implementations.

        // Will not work on some implementations
        q = "SELECT SUBSTRING(e.name, 1, 1)" +
                " FROM Employee e" +
                " GROUP BY SUBSTRING(e.name, 1, 1)";
        System.out.println("First letter: "+em.createQuery(q)
                .getResultList());


        //GROUP BY with Aggregate Functions
        //JPQL supports the five aggregate functions of SQL:
        //
        //COUNT - returns a long value representing the number of elements.
        //SUM - returns the sum of numeric values.
        //AVG - returns the average of numeric values as a double value.
        //MIN - returns the minimum of comparable values (numeric, strings, dates).
        //MAX - returns the maximum of comparable values (numeric, strings, dates).

        q = "SELECT SUBSTRING(e.name, 1, 1), COUNT(e), COUNT(DISTINCT e.name)" +
                " FROM Employee e" +
                " GROUP BY SUBSTRING(e.name, 1, 1)";
        em.createQuery(q).getResultList().stream().forEach( e-> {
            Object[] o = (Object[])e;
            System.out.println(o[0]+" "+ o[1]+" "+o[2]);
        });

//        countDistinct - is in CriteriaBuilder
//        min, least - return an expression representing the minimum of comparable values.
//        max, greatest - return an expression representing the maximum of comparable values.

        //size in hibernate
        //but count(e.phones) will work in some implementations
        q = "select e.phones.size from Employee e";
        System.out.println("size: "+em.createQuery(q)
                .getResultList());
//        This query works on some implementations but not with others.
// Ideally, as per Section 4.8.5, which says, "The path expression argument
// to COUNT may terminate in either a state field or a association field,
// or the argument to COUNT may be an identification variable.", it is a valid
// query and should work.


        q = "select e.salary from Employee11 e " +
                " where e.salary >= ALL(select (e2.salary) from Employee11 e2)";
        System.out.println("size: "+em.createQuery(q)
                .getResultList());
    }
}
