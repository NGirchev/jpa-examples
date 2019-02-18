package ru.girchev.examples.jpa.domain.chapter9;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Girchev N.A.
 * Date: 18.02.2019
 */
@Data
@AllArgsConstructor
public class CarInfo {
    Long id;
    String name;
}
