package ru.girchev.examples.jpa.domain.chapter4;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Some vendors might allow the schema to be included in the name element
 * of the table without having to specify the schema element—for example,
 * @Table(name="HR.schematest"). Support for inlining the name of the schema
 * with the table name is nonstandard.
 *
 * Некоторые поставщики могут разрешить включение схемы в элемент name таблицы
 * без указания элемента схемы.
 *
 * @author Girchev N.A.
 * Date: 07.02.2019
 */
@Data
@Entity(name = "jpaschema")
@Table(name = "hr.schematest")
public class SchemaTest {

    @Id
    private Long id;
}
