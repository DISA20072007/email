package ru.kkb.isimple.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author denis.fedorov
 */

@Entity
@Table(schema = "XXI", name = "OTD")
public class Branch implements Serializable {

    @Id
    @Column(name = "IOTDNUM")
    private Integer id;

    @Column(name = "COTDNAME")
    private String name;

    @Column(name = "DOTDCLOSE")
    private Timestamp dateClose;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getDateClose() {
        return dateClose;
    }

    public void setDateClose(Timestamp dateClose) {
        this.dateClose = dateClose;
    }
}
