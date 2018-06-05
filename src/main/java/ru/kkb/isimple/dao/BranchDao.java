package ru.kkb.isimple.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kkb.isimple.entities.Branch;

import java.util.List;

/**
 * @author denis.fedorov
 */

@Repository
@Transactional
public class BranchDao {

    @Autowired
    private SessionFactory sessionFactory;

    public BranchDao() { }

    public List<Branch> getBranches() {
        String query = "SELECT branchObject FROM Branch branchObject WHERE dateClose IS NULL ORDER BY id";

        Session session = sessionFactory.getCurrentSession();

        Query<Branch> branches = session.createQuery(query, Branch.class);
        return branches.getResultList();
    }
}
