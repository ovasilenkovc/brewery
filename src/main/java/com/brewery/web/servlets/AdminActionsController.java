package com.brewery.web.servlets;

import com.brewery.admin.model.AdminUser;
import com.brewery.admin.model.RoleTypes;
import com.brewery.admin.model.Roles;
import com.brewery.admin.model.reqwrappers.AdminUserWrapper;
import com.brewery.admin.model.reqwrappers.RolesWrapper;
import com.brewery.exceptions.UserNotFoundException;
import com.brewery.services.usersService.UsersManagingService;
import com.brewery.utils.ConstantParams;
import com.brewery.utils.Utils;
import com.brewery.utils.ResponseMaker;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.InvalidRoleValueException;
import javax.validation.Valid;
import java.io.Serializable;
import java.util.*;

@Controller
public class AdminActionsController {

    @Autowired
    private UsersManagingService usersManagingService;

    private static final Logger LOGGER = Logger.getLogger(AdminActionsController.class);

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
    public ResponseEntity<String> createUser(@RequestBody @Valid AdminUserWrapper adminUser) throws InvalidRoleValueException {

        AdminUser user = Utils.toAdminUser(adminUser);
        Map<String, Serializable> errorMap = Utils.isRolesValid(user.getRoles());

        if (!errorMap.isEmpty()) {
            return ResponseMaker.makeResponse(errorMap, ConstantParams.JSON_HEADER_TYPE, HttpStatus.BAD_REQUEST);
        }
        usersManagingService.add(user);
        String message = "User has been added successfully!";
        LOGGER.info(message);
        return ResponseMaker.makeResponse(message, ConstantParams.JSON_HEADER_TYPE, HttpStatus.OK);
    }

    @ResponseBody
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @RequestMapping(value = "/admin/users/", method = RequestMethod.PUT)
    public ResponseEntity<String> updateUserProfile(@RequestBody @Valid AdminUserWrapper adminUserWrapper) {

        AdminUser user = new AdminUser(
                adminUserWrapper.getUsername(),
                adminUserWrapper.getPassword(),
                adminUserWrapper.isEnabled());

        Map<String, Serializable> errorMap = Utils.isRolesValid(user.getRoles());
        if (!errorMap.isEmpty()) {
            return ResponseMaker.makeResponse(errorMap, ConstantParams.JSON_HEADER_TYPE, HttpStatus.BAD_REQUEST);
        }

        usersManagingService.update(user);
        String message = "User has been updated successfully!";
        LOGGER.info(message);
        return ResponseMaker.makeResponse(message, ConstantParams.JSON_HEADER_TYPE, HttpStatus.OK);
    }

    @ResponseBody
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @RequestMapping(value = "/admin/users", method = RequestMethod.DELETE)
    public ResponseEntity<String> removeUser(@RequestBody @Valid AdminUserWrapper adminUserWrapper) {

        AdminUser user = usersManagingService.obtainUser(adminUserWrapper.getUsername());
        if (user == null) {
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
    public ResponseEntity<String> addNewRole(@RequestBody @Valid RolesWrapper role) {

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
    public ResponseEntity<String> removeRole(@RequestBody @Valid RolesWrapper role) {

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

    @ResponseBody
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @RequestMapping(value = "/admin/users/roles", method = RequestMethod.GET)
    public ResponseEntity<String> getAllAvailableRoles() {
        String[] rolesArr = Arrays.toString(RoleTypes.values()).replaceAll("^.|.$", "").split(",");
        List<String> roles = new ArrayList<>();

        for (String role: rolesArr){
            String trimmedName = role.trim();
            roles.add(trimmedName);
        }
        return ResponseMaker.makeResponse(roles, ConstantParams.JSON_HEADER_TYPE, HttpStatus.OK);
    }
}
