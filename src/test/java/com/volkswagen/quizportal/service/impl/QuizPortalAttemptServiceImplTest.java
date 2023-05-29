package com.volkswagen.quizportal.service.impl;

import com.volkswagen.quizportal.exception.EmptyList;
import com.volkswagen.quizportal.exception.UserNotExists;
import com.volkswagen.quizportal.model.QuizPortalAttempt;
import com.volkswagen.quizportal.model.QuizPortalQuestion;
import com.volkswagen.quizportal.model.QuizPortalTopic;
import com.volkswagen.quizportal.model.QuizPortalUser;
import com.volkswagen.quizportal.payload.*;
import com.volkswagen.quizportal.repository.QuizPortalAttemptRepository;
import com.volkswagen.quizportal.repository.QuizPortalTopicRepository;
import com.volkswagen.quizportal.service.QuizPortalAttemptService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class QuizPortalAttemptServiceImplTest {

    @Mock
    private QuizPortalAttemptRequestDTO attemptRequestDTO;

    @Mock
    private QuizPortalAttemptRepository attemptRepository;


    @Mock
    private QuizPortalTopicRepository topicRepository;

    @InjectMocks
    private QuizPortalAttemptServiceImpl quizPortalAttemptService;

    @Mock
    private UserInfoUserDetailsServiceImpl serviceImpl;

    @Mock
    private QuizPortalTopic quizPortalTopic;

    @Mock
    private QuizPortalAttemptResponseDTOMapper attemptResponseDTOMapper;

    @Mock
    private QuizPortalAttemptResponseDTO responseDTO;


    @Mock
    private QuizPortalUser quizPortalUser;


    @Mock
    private QuizPortalAttemptService attemptService;


    @Mock
    private QuizPortalAttempt attempt;

    private QuizPortalTopic topicInDB;

    @BeforeEach
    void beforeEach() {




        attempt = QuizPortalAttempt.builder()
                .marksObtained(4)
                .attemptId(1)
                .topic(topicInDB)
                .build();



        // creating the topic with questions
        Set<QuizPortalQuestion> questionSet = new HashSet<>();
        questionSet.add(new QuizPortalQuestion(1,quizPortalTopic,"","","","","A",""));
        questionSet.add(new QuizPortalQuestion(2,quizPortalTopic,"","","","","B",""));
        questionSet.add(new QuizPortalQuestion(3,quizPortalTopic,"","","","","C",""));
        questionSet.add(new QuizPortalQuestion(4,quizPortalTopic,"","","","","D",""));

        topicInDB = QuizPortalTopic.builder()
                .topicId(1)
                .topicName("Java")
                .description("Testing")
                .marksPerQuestion(1)
                .question(questionSet)
                .publish(0)
                .build();


    }

    @Test
    void saveAttemptedQuiz_GivenQuizPortalAttemptRequestDTO_WhenTopicIdNotFound_ThenThrowException() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        given(topicRepository.findByTopicId(attemptRequestDTO.topicId())).willReturn(Optional.empty());
        org.junit.jupiter.api.Assertions.assertThrows(EmptyList.class, () -> {
            quizPortalAttemptService.saveAttemptedQuiz(attemptRequestDTO);
        });
    }

    @Test
    void saveAttemptedQuiz_GivenQuizPortalAttemptRequestDTO_WhenTopicIdFound_ThenSave() throws EmptyList, UserNotExists {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        Set<QuizPortalQuestAndAnswer> questAndAnswer = new HashSet<>();
        questAndAnswer.add(new QuizPortalQuestAndAnswer(1,"A"));
        questAndAnswer.add(new QuizPortalQuestAndAnswer(2,"B"));
        questAndAnswer.add(new QuizPortalQuestAndAnswer(3,"C"));
        questAndAnswer.add(new QuizPortalQuestAndAnswer(4,"D"));
        QuizPortalAttemptRequestDTO attemptRequestDTO = new QuizPortalAttemptRequestDTO(1, questAndAnswer);
        given(topicRepository.findByTopicId(topicInDB.getTopicId())).willReturn(Optional.of(topicInDB));
        quizPortalAttemptService.calculateQuizMark(attemptRequestDTO.questCorrectOpt(),topicInDB.getQuestion(),topicInDB.getMarksPerQuestion());
        given(attemptResponseDTOMapper.apply(Mockito.any(QuizPortalAttempt.class))).willReturn(responseDTO);
        when(attemptRepository.save(Mockito.any(QuizPortalAttempt.class))).thenReturn(attempt);
        QuizPortalAttemptResponseDTO responseDTO = quizPortalAttemptService.saveAttemptedQuiz(attemptRequestDTO);
        assertThat(responseDTO).isNotNull();
    }

    @Test
    void calculateQuizMark_GivenSavedQuesAndAnsAndAttemptQuestAndAns_WhenAttemptQuesAndAnsNotEmpty_ThenCalculateMarks() throws EmptyList {
        Set<QuizPortalQuestion> questionSet = new HashSet<>();
        questionSet.add(new QuizPortalQuestion(1,quizPortalTopic,"","","","","A",""));
        questionSet.add(new QuizPortalQuestion(2,quizPortalTopic,"","","","","B",""));
        questionSet.add(new QuizPortalQuestion(3,quizPortalTopic,"","","","","C",""));
        questionSet.add(new QuizPortalQuestion(4,quizPortalTopic,"","","","","D",""));



        Set<QuizPortalQuestAndAnswer> questAndAnswer = new HashSet<>();
        questAndAnswer.add(new QuizPortalQuestAndAnswer(1,"A"));
        questAndAnswer.add(new QuizPortalQuestAndAnswer(2,"B"));
        questAndAnswer.add(new QuizPortalQuestAndAnswer(3,"C"));
        questAndAnswer.add(new QuizPortalQuestAndAnswer(4,"D"));

        Integer marks = quizPortalAttemptService.calculateQuizMark(questAndAnswer,questionSet,3);


        System.out.println(marks);

    }

    @Test
    void calculateQuizMark_GivenSavedQuesAndAnsAndAttemptQuestAndAns_WhenQuizPortalQuestionEmpty_ThenThrowException() throws EmptyList {
        Set<QuizPortalQuestion> questionSet = new HashSet<>();
        questionSet.add(new QuizPortalQuestion(1,quizPortalTopic,"","","","","A",""));
        questionSet.add(new QuizPortalQuestion(2,quizPortalTopic,"","","","","B",""));
        questionSet.add(new QuizPortalQuestion(3,quizPortalTopic,"","","","","C",""));
        questionSet.add(new QuizPortalQuestion(4,quizPortalTopic,"","","","","D",""));

        questionSet.clear();


        Set<QuizPortalQuestAndAnswer> questAndAnswer = new HashSet<>();
        questAndAnswer.add(new QuizPortalQuestAndAnswer(1,"A"));
        questAndAnswer.add(new QuizPortalQuestAndAnswer(2,"B"));
        questAndAnswer.add(new QuizPortalQuestAndAnswer(3,"C"));
        questAndAnswer.add(new QuizPortalQuestAndAnswer(4,"D"));


        org.junit.jupiter.api.Assertions.assertThrows(EmptyList.class, () -> {
            quizPortalAttemptService.calculateQuizMark(questAndAnswer,questionSet,3);
        });




    }



    @Test
    void calculateQuizMark_GivenSavedQuesAndAnsAndAttemptQuestAndAns_WhenQuizPortalQuestAndAnswerEmpty_ThenThrowException() throws EmptyList {
        Set<QuizPortalQuestion> questionSet = new HashSet<>();
        questionSet.add(new QuizPortalQuestion(1,quizPortalTopic,"","","","","A",""));
        questionSet.add(new QuizPortalQuestion(2,quizPortalTopic,"","","","","B",""));
        questionSet.add(new QuizPortalQuestion(3,quizPortalTopic,"","","","","C",""));
        questionSet.add(new QuizPortalQuestion(4,quizPortalTopic,"","","","","D",""));




        Set<QuizPortalQuestAndAnswer> questAndAnswer = new HashSet<>();
        questAndAnswer.add(new QuizPortalQuestAndAnswer(1,"A"));
        questAndAnswer.add(new QuizPortalQuestAndAnswer(2,"B"));
        questAndAnswer.add(new QuizPortalQuestAndAnswer(3,"C"));
        questAndAnswer.add(new QuizPortalQuestAndAnswer(4,"D"));

        questAndAnswer.clear();


        org.junit.jupiter.api.Assertions.assertThrows(EmptyList.class, () -> {
            quizPortalAttemptService.calculateQuizMark(questAndAnswer,questionSet,3);
        });




    }


    @Test
    void getListOfAttemptsBasedOnLoggedInUser_WhenListOfAttemptsIsEmpty() {

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        given(attemptRepository.findByAttemptedBy((QuizPortalUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal())).willReturn(Optional.empty());
        org.junit.jupiter.api.Assertions.assertThrows(EmptyList.class, () -> {
            quizPortalAttemptService.getListOfAttemptsBasedOnLoggedInUser();
        });
    }

    @Test
    void getListOfAttemptsBasedOnLoggedInUser_WhenListOfAttemptsIsNotEmpty() throws EmptyList {

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        QuizPortalAttempt  attempt1 = QuizPortalAttempt.builder()
                .marksObtained(4)
                .attemptId(1)
                .topic(topicInDB)
                .build();

        QuizPortalAttempt  attempt2 = QuizPortalAttempt.builder()
                .marksObtained(4)
                .attemptId(1)
                .topic(topicInDB)
                .build();

        List<QuizPortalAttempt> listOfQuizAttempts = new ArrayList<>();
        listOfQuizAttempts.add(attempt1);
        listOfQuizAttempts.add(attempt2);

        given(attemptRepository.findByAttemptedBy((QuizPortalUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal())).willReturn(Optional.of(listOfQuizAttempts));
        given(attemptResponseDTOMapper.apply(Mockito.any(QuizPortalAttempt.class))).willReturn(responseDTO);
        List<QuizPortalAttemptResponseDTO> responseDTO = quizPortalAttemptService.getListOfAttemptsBasedOnLoggedInUser();
        assertThat(responseDTO).isNotNull();

    }



}