package com.jiang.user_manage.config.security;

import com.jiang.user_manage.entity.User;
import com.jiang.user_manage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User " + username + " was not found in db");
        }
        // 设置角色
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_" + user.getRole());
        grantedAuthorities.add(grantedAuthority);

        UserDetailsEntity userDetailsEntity = new UserDetailsEntity(user, user.getUsername(),
                user.getPassword(), user.getToken(), grantedAuthorities);
        return userDetailsEntity;
    }
}
