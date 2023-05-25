package com.volkswagen.quizportal.service.impl;

import com.volkswagen.quizportal.exception.UserNotExists;
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


import static org.mockito.BDDMockito.given;

import java.time.LocalDate;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.when;


/**
 * sample naming convention for Test,
 * LessonApi_GivenCreatingCommonLesson_WhenEmptyNamePosted_ThenBadRequestIsReturned
 * */


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
                .publish(0)
                .build();
    }

    /**
     * given - requestDTO
     * when - UserNotExistsWith
     * then - ThrowUserNotExistsException
     * */
    @Test
    void InitiateQuiz_GivenRequestDTO_WhenUserIdNotExists_ThenThrowException() {
        // given - precondition or setup
        given(userRepository.findById(requestDTO.userId())).willReturn(Optional.empty());

        // when - action or the behaviour that we are going to test
        org.junit.jupiter.api.Assertions.assertThrows(UserNotExists.class, () -> {
            topicService.initiateQuiz(requestDTO);
        });
    }


    /**
     * given - requestDTO
     * when - UserExist
     * then - ReturnTopic
     * */
    @Test
    void InitiateQuiz_GivenTopicRequestDTO_WhenUserExists_ThenReturnTopicResponseDTO() throws UserNotExists {

        given(userRepository.findById(requestDTO.userId())).willReturn(Optional.of(quizPortalUser));
        given(topicDTOMapper.apply(Mockito.any(QuizPortalTopic.class))).willReturn(responseDTO);

        when(topicRepository.save(Mockito.any(QuizPortalTopic.class))).thenReturn(quizPortalTopic);

        QuizPortalTopicResponseDTO responseDTO = topicService.initiateQuiz(requestDTO);

        Assertions.assertThat(responseDTO).isNotNull();
    }


    @Test
    void GetListOfTopicCreatedBy_GivenCurrUser_WhenTopicCreatedByListEmpty_ThenThrowsException() {
        /*given(SecurityContextHolder.getContext().getAuthentication()).willReturn(Optional.of());*/
    }

    @Test
    void MaxMarksCalculation_GivenNoOfQuesAndMarksPerQues_WhenMultiplicationResultMatch_ThenTrueReturned() {
        Integer result = IQuizPortalTopicService.maxMarksCalculation(1,2);
        assertEquals(2,result);
    }
}