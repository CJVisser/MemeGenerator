package com.memegenerator.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Arrays;

public enum Role {

    // Regular casing, otherwise an exception: No enum constant com.example.memegenerator.security.Role.User
    
    User(Collections.singletonList(new SimpleGrantedAuthority("User"))), Admin(new ArrayList<GrantedAuthority>(
            Arrays.asList(new SimpleGrantedAuthority("Admin"), new SimpleGrantedAuthority("User"))));

    private final Collection<GrantedAuthority> authorities;

    Role(Collection<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }
}