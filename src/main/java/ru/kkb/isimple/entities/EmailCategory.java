package ru.kkb.isimple.entities;

import org.hibernate.annotations.JoinColumnOrFormula;
import org.hibernate.annotations.JoinColumnsOrFormulas;
import org.hibernate.annotations.JoinFormula;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * @author denis.fedorov
 */

@Entity
@Table(schema = "XXI", name = "cus_laa_lov_val")
@Where(clause = "LOV_ID = 'EMAIL_CATEGORY'")
public class EmailCategory implements Serializable {

    @Column(name = "LOV_ID")
    private String lovId;

    @Id
    @Column(name = "CVALUE")
   // @JoinFormula("TO_NUMBER(value)")
    private String id;

    @Column(name = "CDESCR")
    private String name;

    public String getLovId() {
        return lovId;
    }

    public void setLovId(String lovId) {
        this.lovId = lovId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
