package ru.girchev.examples.jpa.domain.chapter11;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

/**
 * Lifecycle Event Invocation Order
 1. Check whether any default entity listeners exist (see Chapter 12). If they do,
 iterate through them in the order they are defined and look for methods that
 are annotated with the lifecycle event X annotation. Invoke the lifecycle method
 on the listener if a method was found.
 2. Check on the highest mapped superclass or entity in the hierarchy for classes
 that have an @EntityListeners annotation. Iterate through the entity listener
 classes that are listed in the annotation and look for methods that are
 annotated with the lifecycle event X annotation. Invoke the lifecycle method on
 the listener if a method was found.
 3. Repeat step 2 going down the hierarchy on entities and mapped superclasses
 until entity A is reached, and then repeat it for entity A.
 4. Check on the highest mapped superclass or entity in the hierarchy for methods
 that are annotated with the lifecycle event X annotation. Invoke the callback
 method on the entity if a method was found and the method is not also defined
 in entity A with the lifecycle event X annotation on it.
 5. Repeat step 2 going down the hierarchy on entities and mapped superclasses
 until entity A is reached.
 6. Invoke any methods that are defined on A and annotated with the lifecycle
 event X annotation.
 *
 * @author Girchev N.A.
 * Date: 17.02.2019
 */
@Data
@Entity
@Table(schema = "chapter11")
@SqlResultSetMappings({
        @SqlResultSetMapping(name = "TestMapping",
                entities = {
                        @EntityResult(entityClass = Employee.class,
//                                discriminatorColumn = "TYPE",
                                fields = {
                                @FieldResult(name = "id", column = "id"),
                                @FieldResult(name = "dep", column = "department"),
                        })
                })
        ,@SqlResultSetMapping(
                name="FridayEmployeeResult",
                columns={@ColumnResult(name="id", type = Long.class)})
        ,@SqlResultSetMapping(
        name="constrRes",
        classes={
                @ConstructorResult(
                        targetClass=ru.girchev.examples.jpa.domain.chapter11.EmpWrapper.class,
                        columns={
                                @ColumnResult(name="extra", type=int.class),
                                @ColumnResult(name="salary", type=int.class)
                        })})
})
@NamedNativeQueries({
        @NamedNativeQuery(name = "emplFind", query = "select " +
                " e.id as id, e.name as name, e.dep as department " +
                " from chapter11.employee e",
                resultSetMapping = "TestMapping")
        ,@NamedNativeQuery(
                name = "FridayEmployees",
                query = "SELECT e.id FROM chapter11.employee e"
                , resultSetMapping = "FridayEmployeeResult"
        )
        ,@NamedNativeQuery(
                name = "constrQ",
                query = "SELECT e.extra, e.salary FROM chapter11.employee e"
                , resultSetMapping = "constrRes"
        )
})
@ExcludeDefaultListeners
//@ExcludeSuperclassListeners
@EntityListeners({Listener.class})
public class Employee extends AbstractEntity2 {
    String name;
    String dep;
    int salary;
    int extra;

    @ManyToOne
    Employee manager;

    @OneToMany(mappedBy = "manager")
    List<Employee> stuff;

    @Null(groups = {javax.validation.groups.Default.class})
//    @NotNull
//    @AssertTrue
//    @AssertFalse
//    @DecimalMin(value = "0")
//    @DecimalMax(value = "0", inclusive = true)
//    @Max(value = 0)
//    @Min(value = 0)
//    @Digits(integer = 0, fraction = 0) // fraction - дробь
//    @Past // check date is past current time
//    @Future
//    @Size(max=40, min = 2, message = "{javax.validation.constants.Size.message}",
//            groups = {}, payload = {})
//    @Pattern(regexp = "", flags = {
//            Pattern.Flag.CASE_INSENSITIVE,
//            Pattern.Flag.CANON_EQ,
//            Pattern.Flag.COMMENTS,
//            Pattern.Flag.DOTALL,
//            Pattern.Flag.MULTILINE,
//            Pattern.Flag.UNICODE_CASE,
//            Pattern.Flag.UNIX_LINES
//    })
    private Date date;

    @PostPersist
//    @PrePersist

//    @PreUpdate
//    @PostUpdate

//    @PreRemove
//    @PostRemove

//    @PreDestroy
//    @PostLoad
    private void postPersist() {
        System.out.println("ENTITY LISTENER = "+id);
    }

//    GLOBAL LISTENER +49
//    SUPER LISTENER = 49
//    ENTITY LISTENER = 49
}
