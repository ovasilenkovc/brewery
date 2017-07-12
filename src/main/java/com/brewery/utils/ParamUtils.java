package com.brewery.utils;

import com.brewery.admin.model.AdminUser;
import com.brewery.admin.model.Roles;
import com.brewery.admin.model.reqwrappers.AdminUserWrapper;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Helper Util class for managing incoming parameters.
 * Also provides other useful features such as making error response map etc..
 * This class will be updated as necessary.
 */
public class ParamUtils {

    public static String getString(HttpServletRequest request, String param) {
        return request.getParameter(param);
    }

    public static Long getLong(HttpServletRequest request, String param) {
        try {
            return Long.parseLong(getString(request, param));
        } catch (NumberFormatException ignored) {
        }

        return null;
    }

    public static Integer getInteger(HttpServletRequest request, String param) {
        return getInteger(request, param, null);
    }

    public static Integer getInteger(HttpServletRequest request, String param, Integer defaultValue) {
        Integer value = null;
        try {
            value = Integer.parseInt(getString(request, param));
        } catch (NumberFormatException ignored) {
        }

        return value == null ? defaultValue : value;
    }

    public static Integer getInteger(String param) {
        Integer value = null;
        try {
            value = Integer.parseInt(param);
        } catch (NumberFormatException ignored) {
        }
        return value;
    }

    public static Integer getParam(Integer param, Integer defaultValue) {

        Integer value = null;
        try {
            value = param;
        } catch (NumberFormatException ignored) {
        }

        return value == null ? defaultValue : value;
    }

    public static AdminUser toAdminUser(AdminUserWrapper wrapper){
        AdminUser user = new AdminUser(wrapper.getUsername(), wrapper.getPassword(), wrapper.isEnabled());
        Set<Roles> rolesSet = new HashSet<>();

        for (String roleName: wrapper.getRoles()){
            Roles role = new Roles(user, roleName);
            rolesSet.add(role);
        }
        user.setRoles(rolesSet);
        return user;
    }

    public static Map<String, String> makeErrorMap(BindingResult result) {
        Map<String, String> errorMap = new HashMap<String, String>();
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            for (ObjectError error : errors) {
                FieldError fieldError = (FieldError) error;
                if (errorMap.containsKey(fieldError.getField())) continue;
                errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
        }
        return errorMap;
    }

    public static Map<String, String> makeErrorMap(List<FieldError> fieldErrors, List<ObjectError> objectErrors) {
        Map<String, String> errorMap = new HashMap<String, String>();

        for (FieldError error : fieldErrors) {
            errorMap.put((String) error.getRejectedValue(),  error.getDefaultMessage());
        }

        for (ObjectError error: objectErrors){
            errorMap.put(error.getObjectName(), error.getDefaultMessage());
        }
        return errorMap;
    }


}
