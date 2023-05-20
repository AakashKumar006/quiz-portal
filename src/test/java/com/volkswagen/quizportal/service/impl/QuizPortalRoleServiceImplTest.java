package com.volkswagen.quizportal.service.impl;

import com.volkswagen.quizportal.model.QuizPortalRole;
import com.volkswagen.quizportal.repository.QuizPortalRoleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class QuizPortalRoleServiceImplTest {

    @Mock
    private QuizPortalRoleRepository roleRepository;

    @InjectMocks
    private QuizPortalRoleServiceImpl roleService;

    @Mock
    private QuizPortalRole role;

    //mocking the class and interface before each method call
    @BeforeEach
    void setUp() {
        role = QuizPortalRole.builder().roleId(1).roleName("ROLE_ADMIN").roleDescription("testing").roleActive(1).build();
    }

    @Test
    void saveRole_when_roleNotPresent_Test() {
        // given - precondition or setup
        given(roleRepository.findByRoleName(role.getRoleName())).willReturn(Optional.empty()); // when the method will return empty optional
        given(roleRepository.save(role)).willReturn(role);

        // when - action or the behaviour that we are going to test
        QuizPortalRole saveRole = roleService.saveRole(role);

        // then
        org.assertj.core.api.Assertions.assertThat(saveRole).isNotNull();
    }

    @Test
    void updateRole_when_rolePresent_Test() {
        // given - precondition or setup
        given(roleRepository.findByRoleName(role.getRoleName())).willReturn(Optional.of(role)); // when the method will return empty optional

        // when - action or the behaviour that we are going to test
        QuizPortalRole saveRole = roleService.saveRole(role);

        // then
        org.assertj.core.api.Assertions.assertThat(saveRole).isNotNull();
    }
}