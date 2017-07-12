package com.brewery.admin.dao;

import com.brewery.admin.auth.InvalidToken;
import com.brewery.admin.model.AdminUser;
import com.brewery.admin.model.Roles;
import org.hibernate.HibernateException;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;


public interface AdminUserDAO {

    public List<AdminUser> getUsers() throws HibernateException;

    public void addNewAdminUser(AdminUser adminUser) throws HibernateException;

    public void updateUserProfile(AdminUser adminUser) throws HibernateException;

    public void removeUser(AdminUser adminUser) throws HibernateException;

    public void addUserRole(Roles role) throws HibernateException;

    public void removeUserRole(Roles role) throws HibernateException;

    public AdminUser getUserByUsername(String username) throws HibernateException;

    public void invalidateUserToken(InvalidToken token) throws HibernateException;

    public List<InvalidToken> getInvalidatedTokens();

    public boolean isTokenValid(String token) throws HibernateException;

    public void removeInvalidatedToken(InvalidToken invalidToken);

}
