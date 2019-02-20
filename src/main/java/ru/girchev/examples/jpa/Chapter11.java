package ru.girchev.examples.jpa;

import ru.girchev.examples.jpa.domain.chapter11.EmpWrapper;
import ru.girchev.examples.jpa.domain.chapter11.Employee11;
import ru.girchev.examples.jpa.domain.chapter5.maps.Department;

import javax.persistence.*;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Girchev N.A.
 * Date: 13.02.2019
 */
public class Chapter11 {

    private EntityManagerFactory emf;

    public Chapter11(EntityManagerFactory emf) {
        this.emf = emf;
    }


    public void resultSetTest() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Employee11 employee = new Employee11();
        employee.setDep("Dep");
        employee.setExtra(2);
        employee.setSalary(200);
        employee.setName("Name");
        em.persist(employee);

        Employee11 employee2 = new Employee11();
        employee2.setDep("Dep2");
        employee2.setExtra(4);
        employee2.setSalary(400);
        employee2.setName("Name2");
        em.persist(employee2);

        em.getTransaction().commit();

        List<Employee11> resultList = em
                .createNamedQuery("emplFind", Employee11.class)
                .getResultList();
        System.out.println("emplFind: "+resultList);

        List<Long> ids = em
                .createNamedQuery("FridayEmployees", Long.class)
                .getResultList();
        System.out.println("FridayEmployees: "+ids);
        List<EmpWrapper> wrappers = em
                .createNamedQuery("constrQ", EmpWrapper.class)
                .getResultList();
        System.out.println("wrappers: "+wrappers);

        ValidatorFactory factory =
                Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        validator.validate(employee);


        //CAN BE BEFORE
        // em.createQuery("").setLockMode(LockModeType.PESSIMISTIC_WRITE);
        em.getTransaction().begin();
        //IN TRANSACTION
        // OPTIMISTIC READ == OPTIMISTIC
        // em.lock(employee3, LockModeType.OPTIMISTIC);
        // OPTIMISTIC WRITE == OPTIMISTIC_FORCE_INCREMENT
        // em.refresh(employee3, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
        // em.find(Employee.class, employee3.getId(), LockModeType.PESSIMISTIC_READ);
//         em.find(Employee.class, employee3.getId(), LockModeType.PESSIMISTIC_WRITE);
//         em.find(Employee.class, employee3.getId(), LockModeType.PESSIMISTIC_FORCE_INCREMENT);

        Employee11 employee3 = new Employee11();
        employee3.setDep("Dep3");
        employee3.setExtra(3);
        employee3.setSalary(300);
        employee3.setName("Name3");
        em.persist(employee3);

        em.getTransaction().commit();


        em.close();

//        Persistence.getPersistenceUtil().isLoaded(
//                em.find(Employee.class, 42), "phoneNumbers");
//        PersistenceUnitUtil persistenceUnitUtil
//                = em.getEntityManagerFactory().getPersistenceUnitUtil();
//        persistenceUnitUtil.isLoaded(
//                em.find(Employee.class, 42), "phoneNumbers");
    }

    /**
     * OPTIMISTIC READ
     */
    protected long totalSalaryInDepartment(EntityManager em, int deptId) {
        long total = 0;
        Department dept = em.find(Department.class, deptId);
        for (ru.girchev.examples.jpa.domain.chapter5.maps.Employee emp
                : dept.getEmployeeMap().values()) {
            em.lock(emp, LockModeType.OPTIMISTIC);
            total += emp.getPhones().size();
        }
        return total;
    }

    /**
     * OPTIMISTIC WRITE
     */
    public void addManager(EntityManager em, int id, Employee11 emp) {
        Employee11 manager = em.find(Employee11.class, id);
        em.lock(manager, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
        manager.getStuff().add(emp);
        emp.setManager(manager);

        Map<String, Object> properties = new HashMap<>();
        //The EXTENDED scope covers the same functionality as NORMAL.
        // In addition, it’s able to block related entities in a join table
        properties.put("javax.persistence.lock.timeout", 1000L);
        properties.put("javax.persistence.lock.scope", PessimisticLockScope.EXTENDED);
        properties.put("javax.persistence.lock.scope", PessimisticLockScope.NORMAL); //default
        em.find(Employee11.class, id, LockModeType.PESSIMISTIC_FORCE_INCREMENT, properties);
    }
}
