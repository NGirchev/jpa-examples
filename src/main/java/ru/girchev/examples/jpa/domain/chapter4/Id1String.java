package ru.girchev.examples.jpa.domain.chapter4;

import lombok.Data;

import javax.persistence.*;

/**
 * Some vendors might allow the schema to be included in the name element
 * of the table without having to specify the schema element—for example,
 * @Table(name="chapter4.schematest"). Support for inlining the name of the schema
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
@Table(name = "chapter4.id1str")
@NamedQuery(name = "jpaschema.boringQuery", query = "select j from jpaschema j")
public class Id1String {

    /**
     * • Primitive Java types: byte, int, short, long, char
     * • Wrapper classes of primitive Java types: Byte, Integer, Short, Long, Character
     * • String: java.lang.String
     * • Large numeric type: java.math.BigInteger
     * • Temporal types: java.util.Date, java.sql.Date
     */
    @Id
    //without sequenceName generate "hibernate_sequence"
    //@SequenceGenerator(name = "javaseq", sequenceName = "new_seq")

    // default, without @SequenceGenerator generate in postgres seq with name 'id1string_id_seq'
    @GeneratedValue(strategy = GenerationType.IDENTITY
//  ,generator = "empl_gen_java" - from another entity
//  AnnotationException: Unknown Id.generator: empl_gen_java
    )
    private String id;


    /*
-- auto-generated definition
CREATE TABLE id1string
(
  id SERIAL NOT NULL
    CONSTRAINT id1string_pkey
    PRIMARY KEY
);
     */
}
