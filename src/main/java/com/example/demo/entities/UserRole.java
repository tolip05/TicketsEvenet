package com.example.demo.entities;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "user_roles")
public class UserRole extends BaseEntity implements GrantedAuthority {

    private String authority;

    public UserRole() {

    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    @Column(name = "authority",nullable = false)
    public String getAuthority() {
        return this.authority;
    }

}
