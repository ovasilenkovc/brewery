package com.brewery.admin.dao.impl;

import com.brewery.admin.auth.InvalidToken;
import com.brewery.admin.dao.AdminUserDAO;
import com.brewery.admin.model.AdminUser;
import com.brewery.admin.model.Roles;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository(value = "usersDao")
@Transactional
public class AdminUserDaoImpl implements AdminUserDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<AdminUser> getUsers() {
        Session session = sessionFactory.openSession();
        List<AdminUser> adminUsers = (ArrayList<AdminUser>) session.createCriteria(AdminUser.class).list();

        for (AdminUser user : adminUsers) {
            user.getRoles().size();
        }

        session.flush();
        session.close();
        return adminUsers;
    }

    @Override
    public void addNewAdminUser(AdminUser adminUser) {
        sessionFactory.getCurrentSession().save(adminUser);
        for (Roles role: adminUser.getRoles()){
            addUserRole(role);
        }
    }

    @Override
    public void updateUserProfile(AdminUser adminUser) {
        sessionFactory.getCurrentSession().update(adminUser);
    }

    @Override
    public void removeUser(AdminUser adminUser) {
        sessionFactory.getCurrentSession().delete(adminUser);
    }

    @Override
    public void addUserRole(Roles role) {
        sessionFactory.getCurrentSession().saveOrUpdate(role);
    }

    @Override
    public void removeUserRole(Roles role) {
        sessionFactory.getCurrentSession().delete(role);
    }

    @Override
    public AdminUser getUserByUsername(String username) {
        Session session = sessionFactory.openSession();
        AdminUser adminUser = (AdminUser) session.get(AdminUser.class, username);
        adminUser.getRoles().size();
        session.flush();
        session.close();
        return adminUser;
    }

    @Override
    public void invalidateUserToken(InvalidToken token) {
        sessionFactory.getCurrentSession().save(token);
    }

    @Override
    public List<InvalidToken> getInvalidatedTokens() {
        return (ArrayList<InvalidToken>) sessionFactory.getCurrentSession().createCriteria(InvalidToken.class).list();
    }

    @Override
    public boolean isTokenValid(String token) {
        Query query = sessionFactory.getCurrentSession().createQuery("SELECT COUNT (t) FROM InvalidToken t WHERE t.token=:token");
        query.setString("token", token);
        Number count = (Number) query.uniqueResult();
        return !(count.longValue() > 0);
    }

    @Override
    public void removeInvalidatedToken(InvalidToken invalidToken) {
        sessionFactory.getCurrentSession().delete(invalidToken);
    }

}
