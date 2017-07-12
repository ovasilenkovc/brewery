package com.brewery.services.usersService.impl;

import com.brewery.admin.dao.AdminUserDAO;
import com.brewery.admin.model.AdminUser;
import com.brewery.admin.model.Roles;
import com.brewery.services.usersService.UsersManagingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "usersManagingService")
public class UsersManagingServiceImpl implements UsersManagingService {

    private final AdminUserDAO userDAO;
    private static final Logger LOGGER = LoggerFactory.getLogger(UsersManagingServiceImpl.class);

    @Autowired
    public UsersManagingServiceImpl(AdminUserDAO userDAO) {
        this.userDAO = userDAO;
    }


    @Override
    public List<AdminUser> obtainUsers() {
        LOGGER.info("Users obtaining");
        return userDAO.getUsers();
    }

    @Override
    public AdminUser obtainUser(String username) {
        LOGGER.info("User obtaining");
        return userDAO.getUserByUsername(username);
    }

    @Override
    public void add(AdminUser user) {
        LOGGER.info("The new User " + user.getUsername() + "adding");
        userDAO.addNewAdminUser(user);
    }

    @Override
    public void update(AdminUser user) {
        LOGGER.info(user.getUsername() + " metadata update");
        userDAO.updateUserProfile(user);
    }

    @Override
    public void remove(AdminUser user) {
        LOGGER.info("Removing the user");
        userDAO.removeUser(user);
    }

    @Override
    public void addUserRole(Roles role) {
        LOGGER.info("New role " + role.getRole() + " adding for user: " + role.getUser());
        userDAO.addUserRole(role);
    }

    @Override
    public void removeUserRole(Roles role) {
        LOGGER.info("Role " + role.getRole() + " removing for user: " + role.getUser());
        userDAO.removeUserRole(role);
    }
}
