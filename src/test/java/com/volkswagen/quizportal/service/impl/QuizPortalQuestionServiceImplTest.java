package com.volkswagen.quizportal.service.impl;

import com.volkswagen.quizportal.exception.QuestionCountZero;
import com.volkswagen.quizportal.exception.QuestionNotFound;
import com.volkswagen.quizportal.exception.TopicNotFound;
import com.volkswagen.quizportal.model.QuizPortalQuestion;
import com.volkswagen.quizportal.model.QuizPortalTopic;
import com.volkswagen.quizportal.repository.QuizPortalQuestionRepository;
import com.volkswagen.quizportal.repository.QuizPortalTopicRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class QuizPortalQuestionServiceImplTest {

    @Mock
    private QuizPortalQuestionRepository questionRepository;

    @Mock
    private QuizPortalTopicRepository topicRepository;

    @Mock
    private QuizPortalTopic topic;

    @InjectMocks
    private QuizPortalQuestionServiceImpl questionService;

    private QuizPortalQuestion questions1;
    private QuizPortalQuestion questions2;

    @BeforeEach
    void setUp() {
        topic = QuizPortalTopic.builder()
                .topicId(1)
                .topicName("Java")
                .description("Testing")
                .marksPerQuestion(1)
                .publish(0)
                .build();

        questions1 = QuizPortalQuestion.builder()
                .questionId(1)
                .topic(topic)
                .question("Which one of the following is not a Java feature?")
                .optionA("testing")
                .optionB("Use of pointers")
                .optionC("Portable")
                .optionD("Dynamic and Extensible")
                .build();

        questions2 = QuizPortalQuestion.builder()
                .questionId(2)
                .topic(topic)
                .question("Which one of the following is not a Java feature?")
                .optionA("Object-oriented")
                .optionB("Use of pointers")
                .optionC("Portable")
                .optionD("Dynamic and Extensible")
                .build();
    }

    @Test
    void saveListOfQuestion_GivenQuestionListAndTopicId_WhenTopicNotFound_ThenThrowException() {
        List<QuizPortalQuestion> questionList = new ArrayList<>();
        questionList.add(questions1);
        questionList.add(questions2);
        given(topicRepository.findById(1)).willReturn(Optional.empty());
        // when - action or the behaviour that we are going to test
        org.junit.jupiter.api.Assertions.assertThrows(TopicNotFound.class, () -> {
            questionService.saveListOfQuestion(questionList,1);
        });
    }

/*    @Test
    void saveListOfQuestion_GivenQuestionListAndTopicId_WhenTopicFound_ThenSaveQuestions() throws TopicNotFound {
        // given
        List<QuizPortalQuestion> finalList = new ArrayList<>();
        List<QuizPortalQuestion> questionList = new ArrayList<>();
        questionList.add(questions1);
        questionList.add(questions2);
        given(topicRepository.findById(1)).willReturn(Optional.of(topic));
        // when - action or the behaviour that we are going to test
        questionList.stream().map(question -> {
            question.setTopic(topic);
            finalList.add(question);
            return finalList;
        }).collect(Collectors.toList());
        given(questionRepository.saveAll(finalList)).willReturn(questionList);
        // then
        List<QuizPortalQuestion> savedQuestion = questionService.saveListOfQuestion(questionList,1);
        assertThat(savedQuestion).isNotNull();
        assertEquals(2,finalList.size());
    }*/

    @Test
    void getListOfQuestionBasedOnTopicId_GivenTopicId_WhenTopicNotFound_ThenThrowException() {
        given(topicRepository.findByTopicId(topic.getTopicId())).willReturn(Optional.empty());
        org.junit.jupiter.api.Assertions.assertThrows(TopicNotFound.class, () -> {
            questionService.questionListBasedOnTopicId(1);
        });
    }

    @Test
    void getListOfQuestionBasedOnTopicId_GivenTopicId_WhenQuestionCountIsZero_ThenThrowException() {
        Set<QuizPortalQuestion> setOfQuestion = new HashSet<>();
        setOfQuestion.add(questions1);
        setOfQuestion.add(questions2);
        QuizPortalTopic topicWithQuestion = QuizPortalTopic.builder()
                .topicId(2)
                .topicName("Java")
                .description("Testing")
                .marksPerQuestion(1)
                .question(setOfQuestion)
                .publish(0)
                .build();

        given(topicRepository.findByTopicId(topicWithQuestion.getTopicId())).willReturn(Optional.of(topicWithQuestion));
        topicWithQuestion.getQuestion().clear();
        org.junit.jupiter.api.Assertions.assertThrows(QuestionCountZero.class, () -> {
            questionService.questionListBasedOnTopicId(topicWithQuestion.getTopicId());
        });
    }

    @Test
    void getListOfQuestionBasedOnTopicId_GivenTopicId_WhenQuestionCountIsNotZero_ThenReturnSetOfQuestion() throws TopicNotFound, QuestionCountZero {
        Set<QuizPortalQuestion> setOfQuestion = new HashSet<>();
        setOfQuestion.add(questions1);
        setOfQuestion.add(questions2);
        QuizPortalTopic topicWithQuestion = QuizPortalTopic.builder()
                .topicId(2)
                .topicName("Java")
                .description("Testing")
                .marksPerQuestion(1)
                .question(setOfQuestion)
                .publish(0)
                .build();
        given(topicRepository.findByTopicId(topicWithQuestion.getTopicId())).willReturn(Optional.of(topicWithQuestion));
        Set<QuizPortalQuestion> questionSet = questionService.questionListBasedOnTopicId(topicWithQuestion.getTopicId());
        assertThat(questionSet).isNotNull();
        assertEquals(2,questionSet.size());
    }

    @Test
    void deleteQuestion_GivenQuestionId_WhenQuestionNotFound_ThenThrowException() {
        given(questionRepository.findById(questions1.getQuestionId())).willReturn(Optional.empty());
        org.junit.jupiter.api.Assertions.assertThrows(QuestionNotFound.class, () -> {
            questionService.deleteQuestion(questions1.getQuestionId());
        });
    }

    @Test
    void deleteQuestion_GivenQuestionId_WhenQuestionFound_ThenDeleteQuestion() throws QuestionNotFound {
        given(questionRepository.findById(questions1.getQuestionId())).willReturn(Optional.of(questions1));
        // when
        questionService.deleteQuestion(questions1.getQuestionId());
        // then
        Mockito.verify(questionRepository, times(1)).delete(questions1);
    }

    @Test
    void updateQuestion_GivenQuestionIdAndQuestion_WhenQuestionNotFound_ThenThrowException() {
        Integer questionId = 1;
        QuizPortalQuestion questionsToUpdate = QuizPortalQuestion.builder()
                .topic(topic)
                .question("Which one of the following is not a Java feature?")
                .optionA("Object-oriented")
                .optionB("Use of pointers")
                .optionC("Portable")
                .optionD("Dynamic and Extensible")
                .build();
        given(questionRepository.findById(questionId)).willReturn(Optional.empty());
        org.junit.jupiter.api.Assertions.assertThrows(QuestionNotFound.class, () -> {
            questionService.updateQuestion(questionId,questionsToUpdate);
        });
    }

    @Test
    void updateQuestion_GivenQuestionIdAndQuestion_WhenQuestionFound_ThenUpdateQuestion() throws QuestionNotFound {
        Integer questionId = 1;
        QuizPortalQuestion questionsToUpdate = QuizPortalQuestion.builder()
                .topic(topic)
                .question("Which one of the following is not a Java feature?")
                .optionA("Object-oriented")
                .optionB("Use of pointers")
                .optionC("Portable")
                .optionD("Dynamic and Extensible")
                .build();
        given(questionRepository.findById(questionId)).willReturn(Optional.of(questions1));
        given(questionRepository.save(questionsToUpdate)).willReturn(questionsToUpdate);
        QuizPortalQuestion questionAfterUpdate = questionService.updateQuestion(questionId,questionsToUpdate);
        assertEquals("Object-oriented", questionAfterUpdate.getOptionA());

    }
}