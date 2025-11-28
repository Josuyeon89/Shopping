package com.kt.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class TechUpAuthenticationToken extends AbstractAuthenticationToken {
    private final DefaultCurrentUser currentUser;

    public TechUpAuthenticationToken (DefaultCurrentUser currentUser,
                                      Collection<? extends GrantedAuthority> authorities) {
         super(authorities);
         super.setAuthenticated(true);
         this.currentUser = currentUser;
    }
    @Override
    public Object getCredentials() {
        return currentUser.getId();
    }

    @Override
    public Object getPrincipal() {
        return currentUser;
    }
}
