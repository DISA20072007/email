package ru.kkb.isimple.entities;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @author denis.fedorov
 */

@Embeddable
public class EmailPK implements Serializable {

    @Column(name = "TOPIC_ID")
    private int topicId;

    @Column(name = "BRANCH_ID")
    private int branchId;

    @Column(name = "CATEGORY_ID")
    private int categoryId;

    public EmailPK() { }

    public EmailPK(int topicId, int branchId, int categoryId) {
        this.topicId = topicId;
        this.branchId = branchId;
        this.categoryId = categoryId;
    }
}


