package ru.girchev.examples.jpa;

import ru.girchev.examples.jpa.domain.chapter5.Cat;
import ru.girchev.examples.jpa.domain.chapter5.Item;
import ru.girchev.examples.jpa.domain.chapter5.Person;
import ru.girchev.examples.jpa.domain.chapter5.Phone;
import ru.girchev.examples.jpa.domain.chapter5.maps.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * • Use the @MapKeyClass and targetEntity/targetClass elements of the relationship
 *      and element collection mappings to specify the classes when an untyped Map is used.
 * • Use @MapKey with one-to-many or many-to-many relationship Map that is keyed on
 *      an attribute of the target entity.
 * • Use @MapKeyJoinColumn to override the join column of the entity key.
 * • Use @Column to override the column storing the values of an element collection of basic types.
 * • Use @MapKeyColumn to override the column storing the keys when keyed by a basic type.
 * • Use @MapKeyTemporal and @MapKeyEnumerated if you need to further qualify a
 *      basic key that is a temporal or enumerated type.
 * • Use @AttributeOverride with a “key.” or “value.” prefix to override the column of
 *      an embeddable attribute type that is a Map key or a value, respectively.
 *
 * @author Girchev N.A.
 * Date: 10.02.2019
 */
public class Chapter5 {

    private EntityManagerFactory emf;

    public Chapter5(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void makeCollections() throws ParseException {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Person person = new Person();
        List<String> phones = new ArrayList<String>();
        phones.add("987");
        phones.add("234");
        phones.add("123");
        person.setPhones(phones);

        List<Item> items = new ArrayList<Item>();
        items.add(Item.builder().phone(Phone.builder().model("Iphone").build()).build());
        items.add(Item.builder().phone(Phone.builder().model("Apple").build()).build());
        items.add(Item.builder().phone(Phone.builder().model("Bubble").build()).build());
        person.setItems(items);

        List<Cat> cats = new ArrayList<>();
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        cats.add(new Cat("Stepan", df.parse("12.01.2017")));
        cats.add(new Cat("Lucky", df.parse("12.01.2016")));
        cats.add(new Cat("Murzik", df.parse("12.01.2015")));
        person.setCats(cats);

        em.persist(person);
        em.getTransaction().commit();

        // TEST OF UPDATEBLE=false COLUMN
        em.getTransaction().begin();
        // updates in persistence context only, but exceptions or warnings nothing
        Cat cat = em.find(Cat.class, cats.get(2).getId());
        cat.setName("Tom");
        em.merge(cat);
        em.getTransaction().commit();

        em.getTransaction().begin();
        //updates even updatable is false
        Query nativeQuery = em.createNativeQuery(
                "update chapter5.cat set name = 'Cat' where name like 'Lucky'");
        nativeQuery.executeUpdate();
        em.getTransaction().commit();

        em.getTransaction().begin();
        //updates even updatable is false
        Query simpleQuery = em.createQuery(
                "update Cat as c set c.name = 'Red' where c.name like 'Stepan'");
        simpleQuery.executeUpdate();
        em.getTransaction().commit();

        // from persistence context. Right OrderColumn
        cats = em.find(Person.class, person.getId()).getCats();
        System.out.println("ORDER_CATS_TEST1: " + cats);
        // by jpql
        System.out.println("ORDER_CATS_TEST2: "
                + em.createQuery("select c from Cat c").getResultList());

        //for sorting we need to create new persistence context and new query
        em = emf.createEntityManager();
        em.getTransaction().begin();

        phones = em.find(Person.class, person.getId()).getPhones();
        items = em.find(Person.class, person.getId()).getItems();
        cats = em.find(Person.class, person.getId()).getCats();
        // Right OrderColumn
        System.out.println("ORDER_CATS_TEST3: " + cats);
        System.out.println("ORDER_PHONES_TEST: " + phones);
        System.out.println("ORDER_ITEMS_TEST: " + items);
        em.getTransaction().commit();

        em.close();
    }

    public void makeMaps() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Department department1 = new Department();

        Employee employee1 = new Employee();
        employee1.setName("Ivanov");
        employee1.getPhones().put("WORK", "98356464");
        employee1.getPhones().put("HOME", "43634767");

        department1.getEmployeeMap3().put("223", employee1);
        employee1.setDepartment(department1);

        Employee employee2 = new Employee();
        employee2.setName("Petrov");
        employee2.getPhones().put("WORK", "436737574");
        department1.getEmployeeMap3().put("245", employee1);
        employee2.setDepartment(department1);

        Employee employee3 = new Employee();
        employee3.setName("Andreev");
        employee3.setDepartment(department1);

        Employee employee4 = new Employee();
        employee4.setName("Popov");

        em.persist(employee1);
        em.persist(employee2);
        em.persist(employee3);

        Department department2 = new Department();
        //change empl name... Bad
        department2.getEmployeeMap().put("NewName", employee4);
        //and there we add new column for employee in db for room number
        department2.getEmployeeMap2().put("1", employee4);
        employee4.setDepartment(department2);
        employee4.setDepartment2(department2);
        em.persist(department2);

        em.getTransaction().commit();

        em.clear();

        System.out.println("Map1 dep1 @MapKey to name = "
                + em.find(Department.class, department1.getId()).getEmployeeMap());
        System.out.println("Map1 dep2 @MapKey changes name = "
                + em.find(Department.class, department2.getId()).getEmployeeMap());
        System.out.println("Map2 @MapKeyColumn to room = "
                + em.find(Department.class, department2.getId()).getEmployeeMap2());
        System.out.println("Map3 @ManyToMany with room = "
                + em.find(Department.class, department1.getId()).getEmployeeMap3());
        System.out.println("Map4 dep1 @MapKey default = "
                + em.find(Department.class, department1.getId()).getEmployeeMap4());
        em.close();

        makeMaps2();
    }

    private void makeMaps2() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Department2 department2 = new Department2();
        Employee2 employee2 = new Employee2();
        employee2.setFirstName("First");
        employee2.setLastName("Last");
        employee2.setEmbeddedTestDepartment(department2);

        Employee2 employee3 = new Employee2();
        employee3.setFirstName("First");
        employee3.setLastName("Last");
        employee3.setEmbeddedTestDepartment(department2);

        em.persist(employee2);
        em.persist(employee3);
        em.getTransaction().commit();
        em.clear();
        // gets only second Employee
        System.out.println("Map5 @Embedded = "
                + em.find(Department2.class, department2.getId()).getEmbeddedTestEmployee());

        em.close();

        makeMaps3();
    }

    private void makeMaps3() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Department3 department3 = new Department3();
        Employee3 employee3 = new Employee3();
        employee3.setFakeId(999L);
        employee3.setFullname(EmployeeName3.builder()
                .first_Name("Ivan")
                .last_Name("Ivanov")
                .build());

        department3.getEmployees().put(employee3.getFullname(), employee3);

        department3.getEmpInfos().put(employee3.getFullname(), "Info");

        department3.getSeniorities().put(employee3, 133);

        em.persist(employee3);
        em.persist(department3);

        em.getTransaction().commit();
        em.close();
    }
}
