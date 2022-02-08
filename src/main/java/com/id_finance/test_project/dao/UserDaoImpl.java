package com.id_finance.test_project.dao;


import com.id_finance.test_project.entity.User;
import com.id_finance.test_project.model.UserInfo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class UserDaoImpl implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    public List<UserInfo> showAll() {
        String sql = "Select new " + UserInfo.class.getName()
                + " (e.username,e.symbol,e.price) " + " from "
                + User.class.getName() + " e ";
        Session session = this.sessionFactory.getCurrentSession();
        Query<UserInfo> query = session.createQuery(sql, UserInfo.class);
        return query.getResultList();
    }

    public void createUser(User user) {
        Session session = this.sessionFactory.getCurrentSession();
        session.saveOrUpdate(user);
    }

}
