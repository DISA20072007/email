package ru.kkb.isimple.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kkb.isimple.entities.EmailCategory;

import java.util.List;

/**
 * @author denis.fedorov
 */

@Repository
@Transactional
public class EmailCategoryDao {

    @Autowired
    private SessionFactory sessionFactory;

    public EmailCategoryDao() { }

    public List<EmailCategory> getCategories() {
        String query = "SELECT category FROM EmailCategory category WHERE lovId = 'EMAIL_CATEGORY' ORDER BY id";

        Session session = sessionFactory.getCurrentSession();

        Query<EmailCategory> categories = session.createQuery(query, EmailCategory.class);
        return categories.getResultList();
    }
}
