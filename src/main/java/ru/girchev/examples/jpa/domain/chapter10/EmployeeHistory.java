package ru.girchev.examples.jpa.domain.chapter10;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Girchev N.A.
 * Date: 13.02.2019
 */
@Data
@Entity
@Table(schema = "chapter10")
public class EmployeeHistory implements Serializable{

    @Id
    @OneToOne
    @JoinColumns({
            @JoinColumn(name = "emp_id", referencedColumnName = "id"),
            @JoinColumn(name = "emp_country", referencedColumnName = "country")
    })
    private EmployeeExt employee;
}
