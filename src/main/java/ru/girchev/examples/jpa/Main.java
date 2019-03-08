package ru.girchev.examples.jpa;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.text.ParseException;

/**
 *
 * The onMessage method of a MDB can only have Required or NotSupported.
 *
 *
 There are two transaction-management types supported by JPA. The first is resource-local
 transactions, which are the native transactions of the JDBC drivers that are referenced by a
 persistence unit. The second transaction-management type is JTA transactions, which are the
 transactions of the Java EE server, supporting multiple participating resources, transaction lifecycle
 management, and distributed XA transactions
 */
public class Main {

    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestPU");

    public static void main(String[] args) throws ParseException {
        Chapter4 chapter4 = new Chapter4(emf);
        Chapter5 chapter5 = new Chapter5(emf);
        Chapter6 chapter6 = new Chapter6(emf);
        Chapter7 chapter7 = new Chapter7(emf);
        Chapter8 chapter8 = new Chapter8(emf);
        Chapter9 chapter9 = new Chapter9(emf);
        Chapter10 chapter10 = new Chapter10(emf);
        Chapter11 chapter11 = new Chapter11(emf);
        Chapter12 chapter12 = new Chapter12(emf);
        Common common = new Common(emf);

        chapter4.makeIds();
        chapter4.makeRelations();
        chapter5.makeCollections();
        chapter5.makeMaps();

        chapter6.testContext();
        chapter6.joinTransaction();
        chapter6.testRollback();
        chapter6.referenceFind();
        chapter6.cascadeRemove();
        chapter6.oneToOnePersistDetached();
        chapter6.merge();

        chapter7.query1();
        chapter7.bulkOperations();
        chapter7.bulkOperationsCascade();
        chapter7.cascadeWithRemoveWithOrphanRemoval();

        chapter8.polymorphAndInheritance();

        chapter10.init();
        chapter10.dependent();
        chapter10.inheritance();

        chapter11.resultSetTest();
//        chapter11.test1();

        chapter12.test();
        common.test();

        chapter9.init();
        chapter6.persistRemoveMergeTest();

        emf.close();
        System.out.println("FINISHED!!!");
    }
}
