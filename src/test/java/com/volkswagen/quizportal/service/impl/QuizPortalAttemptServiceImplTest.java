package com.volkswagen.quizportal.service.impl;

import com.volkswagen.quizportal.exception.EmptyList;
import com.volkswagen.quizportal.exception.TopicNotFound;
import com.volkswagen.quizportal.exception.UserNotExists;
import com.volkswagen.quizportal.model.QuizPortalUser;
import com.volkswagen.quizportal.payload.QuizPortalAttemptRequestDTO;
import com.volkswagen.quizportal.payload.QuizPortalQuestAndAnswer;
import com.volkswagen.quizportal.repository.QuizPortalTopicRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QuizPortalAttemptServiceImplTest {


    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private QuizPortalAttemptRequestDTO attemptRequestDTO;

    @Mock
    private QuizPortalTopicRepository topicRepository;

    @Mock
    private UsernamePasswordAuthenticationToken token;

   /* @Mock
    private AuthenticationManager authenticationManager;*/

    @InjectMocks
    private QuizPortalAttemptServiceImpl quizPortalAttemptService;

    @Mock
    private UserInfoUserDetailsServiceImpl serviceImpl;

    @Mock
    private QuizPortalUser quizPortalUser;




    @BeforeEach
    void beforeEach() {

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        QuizPortalQuestAndAnswer q1 = new QuizPortalQuestAndAnswer(1,"A");
        QuizPortalQuestAndAnswer q2 = new QuizPortalQuestAndAnswer(2,"B");
        QuizPortalQuestAndAnswer q3 = new QuizPortalQuestAndAnswer(3,"C");
        List<QuizPortalQuestAndAnswer> questAndAnswers = new ArrayList<>();
        questAndAnswers.add(q1);
        questAndAnswers.add(q2);
        questAndAnswers.add(q3);
    }


    @Test
    void saveAttemptedQuiz_GivenQuizPortalAttemptRequestDTO_WhenTopicIdNotFound_ThenThrowException() {

        given(topicRepository.findByTopicId(attemptRequestDTO.topicId())).willReturn(Optional.empty());
        org.junit.jupiter.api.Assertions.assertThrows(EmptyList.class, () -> {
            quizPortalAttemptService.saveAttemptedQuiz(attemptRequestDTO);
        });
    }

}