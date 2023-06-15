package com.volkswagen.quizportal.service;

import com.volkswagen.quizportal.exception.*;
import com.volkswagen.quizportal.model.QuizPortalTopic;
import com.volkswagen.quizportal.payload.QuizPortalTopicRequestDTO;
import com.volkswagen.quizportal.payload.QuizPortalTopicResponseDTO;
import java.util.List;

public interface IQuizPortalTopicService {

    QuizPortalTopicResponseDTO initiateQuiz(QuizPortalTopicRequestDTO quizPortalTopicRequestDTO) throws UserNotExists;

    List<QuizPortalTopicResponseDTO> getListOfTopicCreatedBy() throws UserNotExists, EmptyList;

    List<QuizPortalTopicResponseDTO> getListOfTopicBasedOnPublishStatus(Integer publishStatus) throws EmptyList;

    List<QuizPortalTopicResponseDTO> getListOfAllTopics() throws EmptyList;

    boolean publishQuiz(Integer topicId) throws QuestionCountZero, EmptyList;

    QuizPortalTopic deleteTopic(Integer topicId) throws TopicNotFound;

    QuizPortalTopic updateTopic(Integer topicId, QuizPortalTopic topic) throws TopicNotFound;

    static Integer maxMarksCalculation(Integer numberOfQuestion, Integer marksPerQuestion) {
        return numberOfQuestion*marksPerQuestion;
    }
}
