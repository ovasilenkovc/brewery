package com.brewery.admin.model.reqwrappers;

import java.io.Serializable;

public class RolesWrapper implements Serializable{

    private String username;

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
}
