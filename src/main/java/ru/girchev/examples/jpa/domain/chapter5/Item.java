package ru.girchev.examples.jpa.domain.chapter5;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

/**
 * @author Girchev N.A.
 * Date: 10.02.2019
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Item {

    private String name;
    private String description;
    @Embedded
    private Phone phone;

    @Override
    public String toString() {
        return phone.getModel();
    }
}
