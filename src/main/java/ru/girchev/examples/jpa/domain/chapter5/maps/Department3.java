package ru.girchev.examples.jpa.domain.chapter5.maps;

import lombok.Data;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Girchev N.A.
 * Date: 11.02.2019
 */
@Data
@Entity
@Table(schema = "chapter5")
public class Department3 {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /*
     * Untyped Maps:
     * Map<Long, Employee>
     */
//    @OneToMany(targetEntity=Employee.class, mappedBy="department")
//    @MapKey
//    private Map employees;

    /**
     * Not like in the book, sheet
     */
    @ManyToMany
    @JoinTable(name="DEPT_EMP",
            schema = "chapter5",
            joinColumns = @JoinColumn(name = "DEPT_ID"),
            inverseJoinColumns = @JoinColumn(name = "EMP_ID"))
    @AttributeOverrides({
            @AttributeOverride(
                    name = "first_Name",
                    column = @Column(name = "EMP_FNAME")),
            @AttributeOverride(
                    name = "last_Name",
                    column = @Column(name = "EMP_LNAME"))
    })
    private Map<EmployeeName3, Employee3> employees = new HashMap<EmployeeName3, Employee3>();

    /*
CREATE TABLE dept_emp
(
dept_id BIGINT       NOT NULL
CONSTRAINT fkcttimbp89n97qwypekcu6qv0n
REFERENCES department3,
emp_id  BIGINT       NOT NULL
CONSTRAINT fkedlxmhxjdmndmxme5rcx9nqvr
REFERENCES employee3,
f_name  VARCHAR(255) NOT NULL,
l_name  VARCHAR(255) NOT NULL,
CONSTRAINT dept_emp_pkey
PRIMARY KEY (dept_id, f_name, l_name)
);
*/

    @ElementCollection
    @CollectionTable(schema = "chapter5")
    @AttributeOverrides({
            @AttributeOverride(name="key.first_Name",
                    column=@Column(name="EMP_FNAME")),
            @AttributeOverride(name="key.last_Name",
                    column=@Column(name="EMP_LNAME"))
            })
    private Map<EmployeeName3, String> empInfos = new HashMap<EmployeeName3, String>();



    @ElementCollection
    @CollectionTable(name="EMP_SENIORITY", schema = "chapter5",
            joinColumns = @JoinColumn(name = "department3_id"))
//    @MapKeyJoinColumn  -- in DB will be 'seniorities_key', and @MapKeyJoinColumn - optional
    @MapKeyJoinColumn(name="seniorities_key", referencedColumnName = "fakeId")
    @MapKeyColumn(name="fakeId") //ignoring...
//    @MapKey(name = "fakeId") //-- AnnotationException: Associated class not found: java.lang.Integer
    @Column(name="seniorities")
    private Map<Employee3, Integer> seniorities = new HashMap<Employee3, Integer>();

}
