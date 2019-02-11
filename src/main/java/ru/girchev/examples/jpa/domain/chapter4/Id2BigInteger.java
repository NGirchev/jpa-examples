package ru.girchev.examples.jpa.domain.chapter4;

import lombok.Data;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;

/**
 * @author Girchev N.A.
 * Date: 10.02.2019
 */
@Data
@Entity
@Table(schema = "chapter4")
public class Id2BigInteger {

    @Id
    @TableGenerator(
            name = "tabl_gen_java",
            table = "Id2BigInteger_table_generator",
            pkColumnName = "pk_col",
            pkColumnValue = "ID2BigDecimalGenerator",
            valueColumnName = "val_col",
            allocationSize = 50,
            schema = "chapter4",
            catalog = "randomcatalog",
            initialValue = 0
//            indexes = {},
//            uniqueConstraints = ""
    )
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "tabl_gen_java")
    private BigInteger id;

    /*
-- auto-generated definition
CREATE TABLE id2biginteger
(
  id NUMERIC(19, 2) NOT NULL
    CONSTRAINT id2biginteger_pkey
    PRIMARY KEY
);
     */
}
