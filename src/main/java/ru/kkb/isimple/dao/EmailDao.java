package ru.kkb.isimple.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kkb.isimple.entities.*;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

/**
 * @author denis.fedorov
 */

@Repository
@Transactional
public class EmailDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private EmailTopicDao emailTopicDao;

    public EmailDao() { }

    public List<Email> getEmails() {
        CriteriaBuilder criteriaBuilder = sessionFactory.getCriteriaBuilder();

        CriteriaQuery<Email> criteriaQuery = criteriaBuilder.createQuery(Email.class);
        Root<Email> emails = criteriaQuery.from(Email.class);

        Join<Email, EmailTopic> topicJoin = emails.join("topic");
        Join<Email, Branch> branchJoin = emails.join("branch", JoinType.LEFT);
        Join<Email, EmailCategory> categoryJoin = emails.join("category");
        Predicate categoryPredicate = criteriaBuilder.equal(categoryJoin.get("lovId"), "EMAIL_CATEGORY");
        criteriaQuery.where(categoryPredicate);

        criteriaQuery.orderBy(criteriaBuilder.asc(topicJoin.get("id")));
        criteriaQuery.select(emails);

        EntityManager entityManager = sessionFactory.createEntityManager();

        TypedQuery<Email> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

    public boolean isExist(int topicId) {
        CriteriaBuilder criteriaBuilder = sessionFactory.getCriteriaBuilder();

        EmailTopic topic = emailTopicDao.getMailTopic(topicId);

        CriteriaQuery<Email> criteriaEmailQuery = criteriaBuilder.createQuery(Email.class);

        Root<Email> emails = criteriaEmailQuery.from(Email.class);

        Predicate topicPredicate = criteriaBuilder.equal(emails.get("topic"), topic);
        criteriaEmailQuery.where(topicPredicate);

        criteriaEmailQuery.select(emails);

        EntityManager entityManager = sessionFactory.createEntityManager();

        TypedQuery<Email> query = entityManager.createQuery(criteriaEmailQuery);
        return !query.getResultList().isEmpty();
    }

    public void setEmail(int topicId, int branchId, int categoryId, String emailAddress) {
        Session session = sessionFactory.getCurrentSession();

        Email email = new Email();
        email.setEmailPK(new EmailPK(topicId, branchId, categoryId));
        email.setEmailAddress(emailAddress);

        session.saveOrUpdate(email);
    }

    public void deleteEmail(int topicId, int branchId, int categoryId) {
        Session session = sessionFactory.getCurrentSession();

        Email email = session.find(Email.class, new EmailPK(topicId, branchId, categoryId));
        if (email != null) {
            session.remove(email);
        }
    }
}
