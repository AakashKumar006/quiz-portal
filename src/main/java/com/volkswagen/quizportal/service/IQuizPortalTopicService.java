package com.volkswagen.quizportal.service;

import com.volkswagen.quizportal.exception.EmptyList;
import com.volkswagen.quizportal.exception.QuestionCountZero;
import com.volkswagen.quizportal.exception.UserNotExists;
import com.volkswagen.quizportal.model.QuizPortalTopic;
import com.volkswagen.quizportal.model.QuizPortalUser;
import com.volkswagen.quizportal.payload.QuizPortalTopicRequestDTO;
import com.volkswagen.quizportal.payload.QuizPortalTopicResponseDTO;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.List;

public interface IQuizPortalTopicService {

    QuizPortalTopicResponseDTO initiateQuiz(QuizPortalTopicRequestDTO quizPortalTopicRequestDTO) throws UserNotExists;

    List<QuizPortalTopicResponseDTO> getListOfTopicCreatedBy() throws UserNotExists, EmptyList;

    List<QuizPortalTopicResponseDTO> getListOfAllPublishedQuiz() throws EmptyList;

    boolean publishQuiz(Integer topicId) throws QuestionCountZero, EmptyList;

    static Integer maxMarksCalculation(Integer numberOfQuestion, Integer marksPerQuestion){
        return numberOfQuestion*marksPerQuestion;
    };
}
