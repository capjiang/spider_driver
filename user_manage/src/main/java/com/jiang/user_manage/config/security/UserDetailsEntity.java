package com.jiang.user_manage.config.security;

import com.jiang.user_manage.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserDetailsEntity implements UserDetails {
    private User user;
    private String username;
    private String password;
    private String token;
    private Collection<? extends GrantedAuthority> authorities;         // 权限

    public UserDetailsEntity(User user, String username, String password, String token, Collection<? extends GrantedAuthority> authorities) {
        this.user = user;
        this.username = username;
        this.password = password;
        this.token = token;
        this.authorities = authorities;
    }

    public User getUser() {
        return user;
    }

    public String getToken() {
        return token;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
