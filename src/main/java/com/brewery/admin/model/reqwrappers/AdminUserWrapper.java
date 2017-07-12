package com.brewery.admin.model.reqwrappers;

import java.io.Serializable;
import java.util.ArrayList;

public class AdminUserWrapper implements Serializable {

    private String username;
    private String password;
    private boolean enabled;
    private ArrayList<String> roles;

    public AdminUserWrapper() {
    }

    public AdminUserWrapper(String username, String password, boolean enabled, ArrayList<String> roles) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public ArrayList<String> getRoles() {
        return roles;
    }

    public void setRoles(ArrayList<String> roles) {
        this.roles = roles;
    }
}
