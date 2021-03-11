  
package com.memegenerator.security;

import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityContextUtil {

    private SecurityContextUtil() {
    }

    
    /** 
     * @return UserDetailsAdapter
     */
    public static UserDetailsAdapter getSecurityContextUser() {
        
        return (UserDetailsAdapter) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}