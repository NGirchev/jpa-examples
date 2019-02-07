package ru.girchev.examples.jpa.domain.chapter4;

import lombok.Data;

import javax.persistence.*;

/**
 * - Mixed Access
 * - Mapping Simple Types
 *
 *   • Primitive Java types: byte, int, short, long, boolean, char, float, double
 *   • Wrapper classes of primitive Java types: Byte, Integer, Short, Long, Boolean,
 *      Character, Float, Double
 *   • Byte and character array types: byte[], Byte[], char[], Character[]
 *   • Large numeric types: java.math.BigInteger, java.math.BigDecimal
 *   • Strings: java.lang.String
 *   • Java temporal types: java.util.Date, java.util.Calendar
 *   • JDBC temporal types: java.sql.Date, java.sql.Time, java.sql.Timestamp
 *   • Enumerated types: Any system or user-defined enumerated type
 *   • Serializable objects: Any system or user-defined serializable type
 *
 * - Large Objects
 * - Enumerated Types
 * - Temporal Types
 *
 * @author Girchev N.A.
 * Date: 05.02.2019
 */
@Data
@Entity
@Access(AccessType.FIELD)
//даже не смотря на hbm2ddl.auto=create схема не создаётся, падает с ошибкой пока не создам руками
@Table(name = "employee", schema = "HR", catalog = "randomcatalog") //Some databases support the notion of a catalog.
public class Employee {

    @Id
    @SequenceGenerator(name = "empl_gen", sequenceName = "empl_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "empl_gen")
    private Long id;

    private String name;

    /**
     * только геттеры могут аннотироваться @Access(AccessType.PROPERTY)
     */
    @Access(AccessType.PROPERTY)
    @Column(name = "emp_name")
    public String getName(){return name;}

    @Transient
    private String phone;

    @Basic(fetch = FetchType.LAZY)
    private String longText;

    /**
     * OID type in DB
     */
    @Basic(fetch=FetchType.LAZY)
    @Lob
    @Column(name="PIC1")
    private byte[] picture;

    /**
     * default type is BYTEA
     * I write 'text' and I can read text in DB in IDEA :-)
     */
    private byte[] pic2;

    @Basic(fetch = FetchType.LAZY)
    @Lob
    private Character[] characters;

    /**
     * EnumType.ORDINAL default, without @Enumerated
     */
    @Enumerated(EnumType.STRING)
    private EmployeeType type1 = EmployeeType.PART_TIME_EMPLOYEE;

    /**
     * EnumType.ORDINAL default, without @Enumerated
     */
    private EmployeeType type2 = EmployeeType.PART_TIME_EMPLOYEE;

    public enum EmployeeType {
        FULL_TIME_EMPLOYEE,
        PART_TIME_EMPLOYEE,
    }
}
