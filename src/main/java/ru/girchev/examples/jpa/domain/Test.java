package ru.girchev.examples.jpa.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author Girchev N.A.
 * Date: 18.02.2019
 */
@Entity
public final class Test {

    @Id
    private Long id;

    private String str;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStr() {
        return str;
    }

    public final void setStr(String str) {
        this.str = str;
    }
}
