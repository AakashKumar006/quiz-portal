package com.volkswagen.quizportal.service;

import com.volkswagen.quizportal.exception.EmptyList;
import com.volkswagen.quizportal.exception.TopicNotFound;
import com.volkswagen.quizportal.model.QuizPortalQuestion;

import java.util.List;

public interface IQuizPortalQuestionService {

    List<QuizPortalQuestion> saveListOfQuestion(QuizPortalQuestion[] quizPortalQuestions, Integer topicId) throws TopicNotFound;

    List<QuizPortalQuestion> questionListBasedOnTopicId(Integer topicId) throws TopicNotFound, EmptyList;
}
