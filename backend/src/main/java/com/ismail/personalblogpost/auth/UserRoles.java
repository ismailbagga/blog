package com.ismail.personalblogpost.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public enum UserRoles {
    SIMPLE_USER(Set.of("BLOG_READ", "BLOG_COMMENT", "BLOG_LIKE")),
    ADMIN(Set.of("BLOG_READ", "BLOG_COMMENT", "BLOG_LIKE", "BLOG_CHANGE", "USER_CHANGE"));

    private Set<String> authorities;
    UserRoles(Set<String> authorities) {
        this.authorities = authorities;
    }

    Set<GrantedAuthority> getGrantedAuthorities() {
        Set<GrantedAuthority> set = authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
        set.add(new SimpleGrantedAuthority("ROLE_"+ this.name())) ;
        return  set  ;
    }

}
