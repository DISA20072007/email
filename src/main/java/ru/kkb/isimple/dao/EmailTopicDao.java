package ru.kkb.isimple.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kkb.isimple.entities.Email;
import ru.kkb.isimple.entities.EmailTopic;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * @author denis.fedorov
 */

@Repository
@Transactional
public class EmailTopicDao {

    @Autowired
    private SessionFactory sessionFactory;

    public EmailTopicDao() { }

    public List<EmailTopic> getMailTopics() {
        String query = "SELECT topic FROM EmailTopic topic ORDER BY id";

        Session session = sessionFactory.getCurrentSession();

        Query<EmailTopic> topics = session.createQuery(query, EmailTopic.class);
        return topics.getResultList();
    }

    public EmailTopic getMailTopic(int topicId) {
        Session session = sessionFactory.getCurrentSession();

        return session.get(EmailTopic.class, topicId);
    }

    public void setTopic(int id, String name) {
        EmailTopic emailTopic = new EmailTopic(id, name);

        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(emailTopic);
    }

    public void deleteTopic(int id) {
        Session session = sessionFactory.getCurrentSession();

        EmailTopic emailTopic = session.find(EmailTopic.class, id);
        if (emailTopic != null) {
            session.remove(emailTopic);
        }
    }
}
