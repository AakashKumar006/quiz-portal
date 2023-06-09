package com.volkswagen.quizportal.service;

import com.volkswagen.quizportal.exception.EmptyList;
import com.volkswagen.quizportal.exception.QuestionCountZero;
import com.volkswagen.quizportal.exception.QuestionNotFound;
import com.volkswagen.quizportal.exception.TopicNotFound;
import com.volkswagen.quizportal.model.QuizPortalQuestion;

import java.util.List;
import java.util.Set;

public interface IQuizPortalQuestionService {

    List<QuizPortalQuestion> saveListOfQuestion(List<QuizPortalQuestion> quizPortalQuestions, Integer topicId) throws TopicNotFound;

    Set<QuizPortalQuestion> questionListBasedOnTopicId(Integer topicId) throws TopicNotFound, EmptyList, QuestionNotFound, QuestionCountZero;

    QuizPortalQuestion deleteQuestion(Integer questionId) throws QuestionNotFound;

    QuizPortalQuestion updateQuestion(Integer questionId, QuizPortalQuestion question) throws QuestionNotFound;
}
