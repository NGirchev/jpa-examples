package ru.girchev.examples.jpa.domain.chapter10.multiple_table;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Girchev N.A.
 * Date: 16.02.2019
 */
@Entity
@Table(schema = "chapter10")
@SecondaryTables({
        @SecondaryTable(name = "address", schema = "chapter10", pkJoinColumns = {
                @PrimaryKeyJoinColumn(name = "user_id"),
                @PrimaryKeyJoinColumn(name = "country"),
        })
//Caused by: org.postgresql.util.PSQLException: ERROR: insert or update on table
// "employeeext" violates foreign key constraint "fk7mki7gxeuk2nrp39yygxhbd4h"
//  Detail: Key (country, id)=(Russia, 39) is not present in table "user".

//        @SecondaryTable(name = "employeeext", schema = "chapter10", pkJoinColumns = {
//                @PrimaryKeyJoinColumn(name = "id", referencedColumnName = "user_id"),
//                @PrimaryKeyJoinColumn(name = "country", referencedColumnName = "country")
//        })
})
@Data
//@IdClass(User.UserId.class)
public class User {


//    @Id
//    Long user_id;
//
//    @Id
//    String country;

    @EmbeddedId
    UserId userId;

    @Column(table = "address", name = "streetName")
    String street;

//    @Column(table = "employeeext", name = "name")
//    String name;

    @Data
    @Embeddable
    static class UserId implements Serializable {
        Long user_id;
        String country;
    }
}

