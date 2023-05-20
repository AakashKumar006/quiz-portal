package com.volkswagen.quizportal.service;

import com.volkswagen.quizportal.model.QuizPortalUser;
import com.volkswagen.quizportal.model.QuizPortalUserRoleMap;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


public class UserInfoUserDetailsService extends QuizPortalUser implements UserDetails {

    private String userEmail;
    private String password;
    private List<SimpleGrantedAuthority> authorityList;

    public UserInfoUserDetailsService(QuizPortalUser quizPortalUser) {
        this.userEmail = quizPortalUser.getUserEmail();
        this.password = quizPortalUser.getUserPassword();
        String role="";
        for(QuizPortalUserRoleMap userRoleMap : quizPortalUser.getUserRoleMap()){
            role=userRoleMap.getRoles().getRoleName();
        }
        List<SimpleGrantedAuthority> authority = Collections.singletonList(new SimpleGrantedAuthority(role));
        this.authorityList = authority;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorityList;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userEmail;
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
