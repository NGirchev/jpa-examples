package ru.girchev.examples.jpa.domain.chapter5.maps;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Map;

/**
 * @author Girchev N.A.
 * Date: 10.02.2019
 */
@Data
@Entity
@ToString(exclude = {"department", "department2", "departments"})
@Table(schema = "chapter5")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ElementCollection(targetClass = String.class)
    @CollectionTable(name = "employee_phones", schema = "chapter5")
    @MapKeyClass(String.class)
    @MapKeyColumn(name = "phones_key")
    @Column(name = "phones")
    private Map phones;

    /*
    -- auto-generated definition
CREATE TABLE employee_phones
(
  employee_id BIGINT       NOT NULL
    CONSTRAINT fk4p7jmy4u3xnk9f207mo32h4uf
    REFERENCES employee,
  number      VARCHAR(255),
  type        VARCHAR(255) NOT NULL,
  CONSTRAINT employee_phones_pkey
  PRIMARY KEY (employee_id, type)
);
     */


    @ElementCollection
    @CollectionTable(name = "employee_phones_enumerated", schema = "chapter5")
//   @MapKeyTemporal -- if use java.util.Date
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name = "type")
    @Column(name = "number")
    private Map<PhoneType, String> phonesByType;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Department department;

    @ManyToOne(cascade = CascadeType.PERSIST) //department2_id
    private Department department2;

    enum PhoneType {
        HOME, MOBILE, WORK
    }
}
