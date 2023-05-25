package com.volkswagen.quizportal.service;

import com.volkswagen.quizportal.exception.EmptyList;
import com.volkswagen.quizportal.exception.QuestionNotFound;
import com.volkswagen.quizportal.exception.TopicNotFound;
import com.volkswagen.quizportal.model.QuizPortalQuestion;
import com.volkswagen.quizportal.payload.QuizPortalAttemptRequestDTO;

import java.util.List;

public interface IQuizPortalQuestionService {

    List<QuizPortalQuestion> saveListOfQuestion(List<QuizPortalQuestion> quizPortalQuestions, Integer topicId) throws TopicNotFound;

    List<QuizPortalQuestion> questionListBasedOnTopicId(Integer topicId) throws TopicNotFound, EmptyList;

    QuizPortalQuestion deleteQuestion(Integer questionId) throws QuestionNotFound;

    QuizPortalQuestion updateQuestion(Integer questionId, QuizPortalQuestion question) throws QuestionNotFound;
}
