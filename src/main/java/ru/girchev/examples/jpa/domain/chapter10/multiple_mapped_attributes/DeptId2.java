package ru.girchev.examples.jpa.domain.chapter10.multiple_mapped_attributes;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Girchev N.A.
 * Date: 14.02.2019
 */
@Data
public class DeptId2 implements Serializable {

    private Long number;
    private String country;
}
