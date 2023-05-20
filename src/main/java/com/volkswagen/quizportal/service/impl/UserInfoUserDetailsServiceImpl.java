package com.volkswagen.quizportal.service.impl;

import com.volkswagen.quizportal.model.QuizPortalUser;
import com.volkswagen.quizportal.repository.QuizPortalUserRepository;
import com.volkswagen.quizportal.service.UserInfoUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserInfoUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private QuizPortalUserRepository quizPortalUserRepository;

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        Optional<QuizPortalUser> quizPortalUser = quizPortalUserRepository.findByUserEmail(userEmail);
        return quizPortalUser.map(UserInfoUserDetailsService::new).orElseThrow(()->new UsernameNotFoundException("user not found"));
    }
}
