package com.memegenerator.backend.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Optional;

import com.memegenerator.backend.data.entity.User;

public class UserDetailsAdapter implements UserDetails {

    private User user;

    public UserDetailsAdapter(Optional<User> user) {

        this.user = user.get();
    }

	
    /** 
     * @return Collection<? extends GrantedAuthority>
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return user.getRole().getAuthorities();
    }

    
    /** 
     * @return String
     */
    @Override
    public String getPassword() {

        return user.getPassword();
    }

    
    /** 
     * @return String
     */
    @Override
    public String getUsername() {

        return user.getUsername();
    }

    
    /** 
     * @return boolean
     */
    @Override
    public boolean isAccountNonExpired() {

        return user.isActivated();
    }

    
    /** 
     * @return boolean
     */
    @Override
    public boolean isAccountNonLocked() {

        return user.isActivated();
    }

    
    /** 
     * @return boolean
     */
    @Override
    public boolean isCredentialsNonExpired() {

        return user.isActivated();
    }

    
    /** 
     * @return boolean
     */
    @Override
    public boolean isEnabled() {
        
        return user.isActivated();
    }

    
    /** 
     * @return Long
     */
    public Long getUserId(){
        return user.id;
    }
}