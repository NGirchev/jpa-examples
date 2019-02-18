package ru.girchev.examples.jpa.domain.chapter10;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @author Girchev N.A.
 * Date: 13.02.2019
 */
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeId implements Serializable {

    private Long id;

    private String country;

}
