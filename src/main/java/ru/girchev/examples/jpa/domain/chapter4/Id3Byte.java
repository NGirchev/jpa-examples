package ru.girchev.examples.jpa.domain.chapter4;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Girchev N.A.
 * Date: 10.02.2019
 */
@Data
//@Entity
@Table(schema = "chapter4")
public class Id3Byte {

    @Id
    @GeneratedValue
    // Caused by: org.hibernate.id.IdentifierGenerationException: Unknown integral data type for ids : java.lang.Byte
    private byte byteId;
}
