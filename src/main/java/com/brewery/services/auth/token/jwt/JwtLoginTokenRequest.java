package com.brewery.services.auth.token.jwt;

import net.sf.oval.constraint.Length;
import net.sf.oval.constraint.MatchPattern;
import net.sf.oval.constraint.NotEmpty;

import java.io.Serializable;

public class JwtLoginTokenRequest implements Serializable{

    @NotEmpty(message = "User name can not be blank")
    @MatchPattern(pattern = "^[a-zA-Z]*", message = "User name must consist of letters!")
    @Length(min = 4, max = 20, message = "the username should consist between 4 to 20 chars!")
    private String username;

    @NotEmpty(message = "password can not be blank")
    @Length(min = 4, max = 20, message = "the password should consist from 4 to 20 chars")
    private String password;

    public JwtLoginTokenRequest() {
    }

    public JwtLoginTokenRequest(String username, String password) {
        this.username = username;
        this.password = password;
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

    @Override
    public String toString() {
        return "JwtLoginTokenRequest{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
