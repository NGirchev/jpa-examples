package ru.girchev.examples.jpa.domain.chapter4.Relations;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Girchev N.A.
 * Date: 10.02.2019
 */
@Data
@Entity
@Table(schema = "chapter4relations")
public class Document_4 {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * without (mappedBy = "documents") - will be created 2 tables:
     * document_employee and employee_document - data will be duplicated
     */
    @ManyToMany(mappedBy = "documents")
    private List<EmployeeRel_4> employees = new ArrayList<EmployeeRel_4>();
}
