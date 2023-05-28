package com.volkswagen.quizportal.service.impl;

import com.volkswagen.quizportal.exception.EmptyList;
import com.volkswagen.quizportal.exception.QuestionCountZero;
import com.volkswagen.quizportal.exception.TopicNotFound;
import com.volkswagen.quizportal.exception.UserNotExists;
import com.volkswagen.quizportal.model.QuizPortalQuestion;
import com.volkswagen.quizportal.model.QuizPortalTopic;
import com.volkswagen.quizportal.model.QuizPortalUser;
import com.volkswagen.quizportal.payload.QuizPortalTopicDTOMapper;
import com.volkswagen.quizportal.payload.QuizPortalTopicRequestDTO;
import com.volkswagen.quizportal.payload.QuizPortalTopicResponseDTO;
import com.volkswagen.quizportal.repository.QuizPortalTopicRepository;
import com.volkswagen.quizportal.repository.QuizPortalUserRepository;
import com.volkswagen.quizportal.service.IQuizPortalTopicService;
import org.assertj.core.api.Assertions;
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


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class QuizPortalTopicServiceImplTest {

    @Mock
    private QuizPortalTopicRequestDTO requestDTO;

    @Mock
    private QuizPortalTopicResponseDTO responseDTO;

    @Mock
    private QuizPortalTopic quizPortalTopic;

    @Mock
    private QuizPortalUser quizPortalUser;

    @Mock
    private QuizPortalTopicDTOMapper topicDTOMapper;

    @Mock
    private QuizPortalUserRepository userRepository;

    @InjectMocks
    private QuizPortalTopicServiceImpl topicService;

    @Mock
    private QuizPortalTopicRepository topicRepository;

    @BeforeEach
    void setUp() {

        Set<QuizPortalQuestion> questionSet = new HashSet<>();
        questionSet.add(new QuizPortalQuestion(1,quizPortalTopic,"","","","","",""));

        quizPortalUser = QuizPortalUser.builder()
                .userId(1)
                .userAge(22)
                .userCreatedOn(LocalDate.now())
                .userDateOfBirth(LocalDate.now())
                .userEmail("aakash.kumar@gmail.com")
                .userFirstName("Akash")
                .userLastName("kumar")
                .userPhoneNo("08750112894")
                .userPassword("Pass@123")
                .build();

        requestDTO = new QuizPortalTopicRequestDTO(
                "Java",
                "Testing",
                5,
                1,
                1);

        quizPortalTopic = QuizPortalTopic.builder()
                .topicId(1)
                .topicName("Java")
                .description("Testing")
                .marksPerQuestion(1)
                .question(questionSet)
                .publish(0)
                .build();
    }

    @Test
    void InitiateQuiz_GivenRequestDTO_WhenUserIdNotExists_ThenThrowException() {
        // given - precondition or setup
        given(userRepository.findById(requestDTO.userId())).willReturn(Optional.empty());

        // when - action or the behaviour that we are going to test
        org.junit.jupiter.api.Assertions.assertThrows(UserNotExists.class, () -> {
            topicService.initiateQuiz(requestDTO);
        });
    }

    @Test
    void InitiateQuiz_GivenTopicRequestDTO_WhenUserExists_ThenReturnTopicResponseDTO() throws UserNotExists {
        given(userRepository.findById(requestDTO.userId())).willReturn(Optional.of(quizPortalUser));
        given(topicDTOMapper.apply(Mockito.any(QuizPortalTopic.class))).willReturn(responseDTO);
        when(topicRepository.save(Mockito.any(QuizPortalTopic.class))).thenReturn(quizPortalTopic);
        QuizPortalTopicResponseDTO responseDTO = topicService.initiateQuiz(requestDTO);
        assertThat(responseDTO).isNotNull();
    }

    @Test
    void GetListOfTopicCreatedBy_GivenCurrUser_WhenTopicCreatedByListEmpty_ThenThrowsException() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        given(topicRepository.findByCreatedBy((QuizPortalUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal())).willReturn(Collections.emptyList());
        org.junit.jupiter.api.Assertions.assertThrows(EmptyList.class, () -> {
            topicService.getListOfTopicCreatedBy();
        });
    }

    @Test
    void GetListOfTopicCreatedBy_GivenCurrUser_WhenTopicCreatedByListNotEmpty_ThenReturnTopicResponseDTO() throws EmptyList {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        List<QuizPortalTopic> listOfTopicCreatedBy = new ArrayList<>();
        listOfTopicCreatedBy.add(quizPortalTopic);
        given(topicRepository.findByCreatedBy((QuizPortalUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal())).willReturn(listOfTopicCreatedBy);
        given(topicDTOMapper.apply(Mockito.any(QuizPortalTopic.class))).willReturn(responseDTO);
        List<QuizPortalTopicResponseDTO> responseDTO = topicService.getListOfTopicCreatedBy();
        assertThat(responseDTO).isNotNull();
    }

    @Test
    void getListOfAllTopics_WhenTopicListEmpty_ThenThrowsException() {
        given(topicRepository.findAll()).willReturn(Collections.emptyList());
        org.junit.jupiter.api.Assertions.assertThrows(EmptyList.class, () -> {
            topicService.getListOfAllTopics();
        });
    }

    @Test
    void getListOfAllTopics_WhenTopicListIsNotEmpty_ThenReturnListOfTopic() throws EmptyList {
        List<QuizPortalTopic> listOfTopicCreatedBy = new ArrayList<>();
        listOfTopicCreatedBy.add(quizPortalTopic);
        given(topicRepository.findAll()).willReturn(listOfTopicCreatedBy);
        given(topicDTOMapper.apply(Mockito.any(QuizPortalTopic.class))).willReturn(responseDTO);
        List<QuizPortalTopicResponseDTO> responseDTO = topicService.getListOfAllTopics();
        assertThat(responseDTO).isNotNull();
        assertThat(listOfTopicCreatedBy.size()).isEqualTo(responseDTO.size());
    }

    @Test
    void publishQuiz_GivenTopicId_WhenTopicNotFound_ThenThrowsEmptyList() {
        Integer topicId = 1;
        given(topicRepository.findByTopicId(topicId)).willReturn(Optional.empty());
        org.junit.jupiter.api.Assertions.assertThrows(EmptyList.class, () -> {
            topicService.publishQuiz(topicId);
        });
    }

    @Test
    void publishQuiz_GivenTopicId_WhenTopicQuestionListSizeZero_ThenThrowQuestionCountZeroException() {
        // given
        Integer topicId = 1;
        given(topicRepository.findByTopicId(topicId)).willReturn(Optional.of(quizPortalTopic));

        // when
        quizPortalTopic.getQuestion().clear();

        // then
        org.junit.jupiter.api.Assertions.assertThrows(QuestionCountZero.class, () -> {
            topicService.publishQuiz(topicId);
        });
    }

    @Test
    void publishQuiz_GivenTopicId_WhenTopicQuestionListSizeNotZero_ThenPublishQuiz() throws EmptyList, QuestionCountZero {
        // given
        Integer topicId = 1;
        // when
        given(topicRepository.findByTopicId(topicId)).willReturn(Optional.of(quizPortalTopic));
        assertEquals(quizPortalTopic.getQuestion().size(),1);
        // then
        topicService.publishQuiz(topicId);
        assertEquals(quizPortalTopic.getPublish(),1);
    }

    @Test
    void deleteTopic_GivenTopicId_WhenTopicNotFound_ThrowTopicNotFoundException() {
        Integer topicId = 1;
        given(topicRepository.findByTopicId(1)).willReturn(Optional.empty());
        org.junit.jupiter.api.Assertions.assertThrows(TopicNotFound.class, () -> {
            topicService.deleteTopic(topicId);
        });
    }

    @Test
    void deleteTopic_GivenTopicId_WhenTopicExists_ReturnDeletedTopic() throws TopicNotFound {
        // given
        given(topicRepository.findByTopicId(1)).willReturn(Optional.of(quizPortalTopic));
        // when
        topicService.deleteTopic(quizPortalTopic.getTopicId());
        // then
        Mockito.verify(topicRepository, times(1)).delete(quizPortalTopic);
    }

    @Test
    void MaxMarksCalculation_GivenNoOfQuesAndMarksPerQues_WhenMultiplicationResultMatch_ThenTrueReturned() {
        Integer result = IQuizPortalTopicService.maxMarksCalculation(1,2);
        assertEquals(2,result);
    }

    @Test
    void getListOfTopicBasedOnPublishStatus_GivenPublishStatusZero_WhenTopicListIsEmpty_ThenThrowException() {
        // given
        Integer publishStatus = 0;
        // when
        given(topicRepository.findByPublish(publishStatus)).willReturn(Collections.emptyList());
        // then
        org.junit.jupiter.api.Assertions.assertThrows(EmptyList.class, () -> {
            topicService.getListOfTopicBasedOnPublishStatus(publishStatus);
        });
    }

    @Test
    void getListOfTopicBasedOnPublishStatus_GivenPublishStatusOne_WhenTopicListIsEmpty_ThenThrowException() {
        // given
        Integer publishStatus = 1;
        // when
        given(topicRepository.findByPublish(publishStatus)).willReturn(Collections.emptyList());
        // then
        org.junit.jupiter.api.Assertions.assertThrows(EmptyList.class, () -> {
            topicService.getListOfTopicBasedOnPublishStatus(publishStatus);
        });
    }

    @Test
    void getListOfTopicBasedOnPublishStatus_GivenPublishStatus_WhenTopicListIsNotEmpty_ThenReturnListOfTopic() throws EmptyList {
        List<QuizPortalTopic> listOfTopic = new ArrayList<>();
        listOfTopic.add(quizPortalTopic);
        // given
        Integer publishStatus = 1;
        // when
        given(topicRepository.findByPublish(publishStatus)).willReturn(listOfTopic);
        // then
        given(topicDTOMapper.apply(Mockito.any(QuizPortalTopic.class))).willReturn(responseDTO);
        List<QuizPortalTopicResponseDTO> topicResponseDTO = topicService.getListOfTopicBasedOnPublishStatus(publishStatus);
        assertThat(topicResponseDTO).isNotNull();
        assertEquals(1,listOfTopic.size());
    }

    @Test
    void updateTopic_givenTopicIdAndTopic_WhenTopicNotFound_ThenThrowException() {
        Integer topicId = 1;
        QuizPortalTopic topicToUpdate = QuizPortalTopic.builder()
                .topicId(1)
                .topicName("Java")
                .description("Testing")
                .marksPerQuestion(1)
                .publish(0)
                .build();
        given(topicRepository.findByTopicId(topicId)).willReturn(Optional.empty());
        org.junit.jupiter.api.Assertions.assertThrows(TopicNotFound.class, () -> {
            topicService.updateTopic(topicId, topicToUpdate);
        });
    }

    @Test
    void updateTopic_givenTopicIdAndTopic_WhenTopicFound_ThenUpdateTopic() throws TopicNotFound {
        Integer topicId = 1;
        QuizPortalTopic topicToUpdate = QuizPortalTopic.builder()
                .topicId(1)
                .topicName("network")
                .description("Testing")
                .marksPerQuestion(1)
                .publish(0)
                .build();
        given(topicRepository.findByTopicId(topicId)).willReturn(Optional.of(quizPortalTopic));
        given(topicRepository.save(topicToUpdate)).willReturn(topicToUpdate);
        QuizPortalTopic topicAfterUpdating = topicService.updateTopic(topicId,topicToUpdate);
        assertEquals(topicAfterUpdating.getTopicName(),"network");
    }
}