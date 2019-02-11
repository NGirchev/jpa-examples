package ru.girchev.examples.jpa.domain.chapter5.maps;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author Girchev N.A.
 * Date: 11.02.2019
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class EmployeeName3 {
    @Column(name="F_NAME")
    private String first_Name;
    @Column(name="L_NAME")
    private String last_Name;
}
