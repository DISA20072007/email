package ru.kkb.isimple.entities;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author denis.fedorov
 */

@Entity
@Table(name = "EMAIL")
public class Email implements Serializable {

    @EmbeddedId
    private EmailPK emailPK;

    @ManyToOne
    @JoinColumn(name = "TOPIC_ID", insertable = false, updatable = false)
    private EmailTopic topic;

    @ManyToOne
    @JoinColumn(name = "BRANCH_ID", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private Branch branch;

    @ManyToOne
    @JoinColumnOrFormula(formula = @JoinFormula(value = "TO_CHAR(CATEGORY_ID)", referencedColumnName = "CVALUE"))
    private EmailCategory category;

    @Column(name = "EMAIL_ADDRESS")
    private String emailAddress;

    public EmailTopic getTopic() {
        return topic;
    }

    public void setTopic(EmailTopic topic) {
        this.topic = topic;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public EmailCategory getCategory() {
        return category;
    }

    public void setCategory(EmailCategory category) {
        this.category = category;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public EmailPK getEmailPK() {
        return emailPK;
    }

    public void setEmailPK(EmailPK emailPK) {
        this.emailPK = emailPK;
    }
}
