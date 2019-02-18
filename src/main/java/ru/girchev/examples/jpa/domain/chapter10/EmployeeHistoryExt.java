package ru.girchev.examples.jpa.domain.chapter10;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Girchev N.A.
 * Date: 14.02.2019
 */
@Data
//@Entity
@Table(schema = "chapter10")
public class EmployeeHistoryExt {

    @Id
    int empId;


    @MapsId
    @OneToOne
    @JoinColumns({
            @JoinColumn(name = "emp_id", referencedColumnName = "id"),
            @JoinColumn(name = "emp_country", referencedColumnName = "country")
    })
    private EmployeeExt employee;
}
