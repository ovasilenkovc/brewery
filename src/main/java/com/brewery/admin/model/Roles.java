package com.brewery.admin.model;

import org.codehaus.jackson.annotate.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "roles", catalog = "brewery")
public class Roles implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id", unique = true, nullable = false)
    private Integer userRoleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username", nullable = false)
    @JsonBackReference
    private AdminUser user;

    @Column(name = "role", nullable = false, length = 70)
    private String role;

    public Roles() {
    }

    public Roles(AdminUser user, String role) {
        this.user = user;
        this.role = role;
    }

    public Integer getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(Integer userRoleId) {
        this.userRoleId = userRoleId;
    }

    public AdminUser getUser() {
        return user;
    }


    public void setUser(AdminUser user) {
        this.user = user;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Roles{" +
                "userRoleId=" + userRoleId +
                ", user=" + user +
                ", role='" + role + '\'' +
                '}';
    }
}
