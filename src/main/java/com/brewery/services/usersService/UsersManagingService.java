package com.brewery.services.usersService;

import com.brewery.admin.model.AdminUser;
import com.brewery.admin.model.Roles;

import java.util.List;

public interface UsersManagingService {

    public List<AdminUser> obtainUsers();

    public AdminUser obtainUser(String username);

    public void add(AdminUser user);

    public void update(AdminUser user);

    public void remove(AdminUser user);

    public void addUserRole(Roles role);

    public void removeUserRole(Roles role);

}
