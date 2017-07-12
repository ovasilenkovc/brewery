package com.brewery.web.servlets;

import com.brewery.admin.model.AdminUser;
import com.brewery.admin.model.Roles;
import com.brewery.admin.model.reqwrappers.AdminUserWrapper;
import com.brewery.admin.model.reqwrappers.RolesWrapper;
import com.brewery.exceptions.UserNotFoundException;
import com.brewery.services.usersService.UsersManagingService;
import com.brewery.utils.ConstantParams;
import com.brewery.utils.ParamUtils;
import com.brewery.utils.ResponseMaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AdminActionsController {

    @Autowired
    private UsersManagingService usersManagingService;

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminActionsController.class);

    @ResponseBody
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @RequestMapping(value = "/admin/users", method = RequestMethod.GET)
    public ResponseEntity<String> obtainAllUsers() {
        Map<String, List<AdminUser>> response = new HashMap<>();
        response.put("users", usersManagingService.obtainUsers());
        LOGGER.info("Users have been obtained successfully!");
        return ResponseMaker.makeResponse(response, ConstantParams.JSON_HEADER_TYPE, HttpStatus.OK);
    }

    @ResponseBody
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @RequestMapping(value = "/admin/users/{username}", method = RequestMethod.GET)
    public ResponseEntity<String> obtainUser(@PathVariable String username) {
        AdminUser adminUser = usersManagingService.obtainUser(username);
        LOGGER.info("User has been obtained successfully!");
        return ResponseMaker.makeResponse(adminUser, ConstantParams.JSON_HEADER_TYPE, HttpStatus.OK);
    }

    @ResponseBody
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @RequestMapping(value = "/admin/users", method = RequestMethod.POST)
    public ResponseEntity<String> createUser(@RequestBody AdminUserWrapper adminUser) {
        AdminUser user = ParamUtils.toAdminUser(adminUser);
        usersManagingService.add(user);

        String message = "User has been added successfully!";
        LOGGER.info(message);
        return ResponseMaker.makeResponse(message, ConstantParams.JSON_HEADER_TYPE, HttpStatus.OK);
    }

    @ResponseBody
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @RequestMapping(value = "/admin/users/", method = RequestMethod.PUT)
    public ResponseEntity<String> updateUserProfile(@RequestBody AdminUserWrapper adminUserWrapper) {

        AdminUser user = new AdminUser(
                adminUserWrapper.getUsername(),
                adminUserWrapper.getPassword(),
                adminUserWrapper.isEnabled());
        usersManagingService.update(user);

        String message = "User has been updated successfully!";
        LOGGER.info(message);
        return ResponseMaker.makeResponse(message, ConstantParams.JSON_HEADER_TYPE, HttpStatus.OK);
    }

    @ResponseBody
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @RequestMapping(value = "/admin/users", method = RequestMethod.DELETE)
    public ResponseEntity<String> removeUser(@RequestBody AdminUserWrapper adminUserWrapper) {

        AdminUser user = usersManagingService.obtainUser(adminUserWrapper.getUsername());
        if(user == null){
            LOGGER.error("User with specified name: " + adminUserWrapper.getUsername() + "not found");
            throw new UserNotFoundException("User with specified name: " + adminUserWrapper.getUsername() + "not found");
        }
        usersManagingService.remove(user);

        String message = "New Role " + user.getUsername() + "has been removed successfully !";
        LOGGER.info(message);
        return ResponseMaker.makeResponse(message, ConstantParams.JSON_HEADER_TYPE, HttpStatus.OK);
    }

    @ResponseBody
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @RequestMapping(value = "/admin/users/roles", method = RequestMethod.POST)
    public ResponseEntity<String> addNewRole(@RequestBody RolesWrapper role) {

        AdminUser user = usersManagingService.obtainUser(role.getUsername());
        if (user == null) {
            LOGGER.error("User with specified name: " + role.getUsername() + "not found");
            throw new UserNotFoundException("User with specified name: " + role.getUsername() + "not found");
        }

        usersManagingService.addUserRole(new Roles(user, role.getRole()));

        String message = "New Role " + role.getRole() + "has been added successfully for " + role.getUsername() + "!";
        LOGGER.info(message);
        return ResponseMaker.makeResponse(message, ConstantParams.JSON_HEADER_TYPE, HttpStatus.OK);
    }

    @ResponseBody
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @RequestMapping(value = "/admin/users/roles", method = RequestMethod.DELETE)
    public ResponseEntity<String> removeRole(@RequestBody RolesWrapper role) {

        AdminUser user = usersManagingService.obtainUser(role.getUsername());

        if (user == null) {
            LOGGER.error("User with specified name: " + role.getUsername() + "not found");
            throw new UserNotFoundException("User with specified name: " + role.getUsername() + "not found");
        }
        usersManagingService.removeUserRole(new Roles(user, role.getRole()));

        String message = "Role " + role.getRole() + "has been removed successfully for " + role.getUsername() + "!";
        LOGGER.info(message);
        return ResponseMaker.makeResponse(message, ConstantParams.JSON_HEADER_TYPE, HttpStatus.OK);
    }

}
