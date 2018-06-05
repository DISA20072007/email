package ru.kkb.isimple.entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author denis.fedorov
 */

@Entity
@Table(name = "EMAIL_TOPICS")
public class EmailTopic implements Serializable {

    @Id
    @Column(name = "ID")
    private int id;

    @Column(name = "NAME")
    private String name;

    public EmailTopic() { }

    public EmailTopic(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EmailTopic that = (EmailTopic) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
