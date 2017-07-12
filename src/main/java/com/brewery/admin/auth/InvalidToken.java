package com.brewery.admin.auth;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "invalidTokens", catalog = "brewery")
public class InvalidToken {

    @Id
    @Column(name = "token", unique = true, nullable = false, length = 250)
    private String token;

    public InvalidToken() {
    }

    public InvalidToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
