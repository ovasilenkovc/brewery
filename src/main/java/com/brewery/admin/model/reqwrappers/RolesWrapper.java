package com.brewery.admin.model.reqwrappers;

import net.sf.oval.constraint.Length;
import net.sf.oval.constraint.MatchPattern;
import net.sf.oval.constraint.NotEmpty;

import java.io.Serializable;

public class RolesWrapper implements Serializable{

    @NotEmpty(message = "User name can not be blank")
    @MatchPattern(pattern = "^[a-zA-Z]*", message = "User name must consist of letters!")
    @Length(min = 4, max = 20, message = "the username should consist between 4 to 20 chars!")
    private String username;

    @NotEmpty(message = "Role name can not be blank")
    @Length(min = 4, max = 30, message = "the role should consist from 4 to 20 chars")
    private String role;

    public RolesWrapper() {
    }

    public RolesWrapper(String username, String role) {
        this.username = username;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "RolesWrapper{" +
                "username='" + username + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
