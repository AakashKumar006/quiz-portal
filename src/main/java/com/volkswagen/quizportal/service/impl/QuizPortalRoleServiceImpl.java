package com.volkswagen.quizportal.service.impl;

import com.volkswagen.quizportal.model.QuizPortalRole;
import com.volkswagen.quizportal.repository.QuizPortalRoleRepository;
import com.volkswagen.quizportal.service.IQuizPortalRoleService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QuizPortalRoleServiceImpl implements IQuizPortalRoleService {

    private final QuizPortalRoleRepository roleRepository;

    public QuizPortalRoleServiceImpl(QuizPortalRoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public QuizPortalRole saveRole(QuizPortalRole role) {
        Optional<QuizPortalRole> roleOptional = roleRepository.findByRoleName(role.getRoleName());
        if(roleOptional.isPresent()){
            return roleOptional.get();
        } else {
            roleRepository.save(role);
            return role;
        }
    }
}
