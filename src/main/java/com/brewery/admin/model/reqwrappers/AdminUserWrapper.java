package com.brewery.admin.model.reqwrappers;

import net.sf.oval.constraint.Length;
import net.sf.oval.constraint.MatchPattern;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;

import java.io.Serializable;
import java.util.ArrayList;

public class AdminUserWrapper implements Serializable {

    @NotEmpty(message = "User name can not be blank")
    @MatchPattern(pattern = "^[a-zA-Z]*", message = "User name must consist of letters!")
    @Length(min = 4, max = 20, message = "the username should consist between 4 to 20 chars!")
    private String username;

    @NotEmpty(message = "password can not be blank")
    @Length(min = 4, max = 20, message = "the password should consist from 4 to 20 chars")
    private String password;

    @NotNull(message = "User enabled marker is required parameter!")
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
