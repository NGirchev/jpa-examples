package ru.girchev.examples.jpa;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 There are two transaction-management types supported by JPA. The first is resource-local
 transactions, which are the native transactions of the JDBC drivers that are referenced by a
 persistence unit. The second transaction-management type is JTA transactions, which are the
 transactions of the Java EE server, supporting multiple participating resources, transaction lifecycle
 management, and distributed XA transactions
 */
public class Main {

    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestPU");

    public static void main(String[] args) {
        Chapter4 chapter4 = new Chapter4(emf);
        Chapter5 chapter5 = new Chapter5(emf);
        Chapter6 chapter6 = new Chapter6(emf);

        chapter4.makeIds();
        chapter4.makeRelations();
        chapter5.makeCollections();
        chapter5.makeMaps();
        chapter6.testContext();
        chapter6.joinTransaction();

        emf.close();
        System.out.println("FINISHED!!!");
    }
}
