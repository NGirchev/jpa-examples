package ru.girchev.examples.jpa.domain.chapter10.multiple_mapped_attributes;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Girchev N.A.
 * Date: 14.02.2019
 */
@Data
public class ProjectId2 implements Serializable {

    private DeptId2 dept2;
    private String name;
}
