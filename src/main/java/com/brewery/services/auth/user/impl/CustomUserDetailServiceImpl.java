package com.brewery.services.auth.user.impl;

import com.brewery.admin.auth.InvalidToken;
import com.brewery.admin.auth.User;
import com.brewery.admin.dao.AdminUserDAO;
import com.brewery.admin.model.AdminUser;
import com.brewery.admin.model.Roles;
import com.brewery.services.auth.user.CustomUserDetailService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository(value = "customUserDetailService")
public class CustomUserDetailServiceImpl implements CustomUserDetailService {

    @Autowired
    private AdminUserDAO userDAO;

    private static final Logger LOGGER = Logger.getLogger(CustomUserDetailService.class);

    @Override
    public User getUserDetailsByUserName(String username) {
        AdminUser adminUser = userDAO.getUserByUsername(username);
        LOGGER.info("The users obtaining started");
        return new User(
                adminUser.getUsername(),
                adminUser.getPassword(),
                adminUser.isEnabled(),
                grantedAuthorities(adminUser.getRoles()));
    }

    public void invalidateToken(String token) {
        InvalidToken invalidToken = new InvalidToken(token);
        userDAO.invalidateUserToken(invalidToken);
        LOGGER.info("Token has been invalidated successfully!");
    }

    @Override
    public boolean isValidToken(String token) {
        LOGGER.info("Checking is current token valid");
        return userDAO.isTokenValid(token);
    }


    private Set<GrantedAuthority> grantedAuthorities(Set<Roles> rolesSet) {
        Set<GrantedAuthority> authorities = new HashSet<>(rolesSet.size());
        for (Roles role : rolesSet) {
            authorities.add(new SimpleGrantedAuthority(role.getRole()));
        }
        return authorities;
    }
}

