package com.brewery.admin.auth;

import javax.persistence.*;

@Entity
@Table(name = "invalidTokens", catalog = "brewery")
public class InvalidToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "token", nullable = false, length = 250)
    private String token;

    public InvalidToken() {
    }

    public InvalidToken(String token) {
        this.token = token;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
