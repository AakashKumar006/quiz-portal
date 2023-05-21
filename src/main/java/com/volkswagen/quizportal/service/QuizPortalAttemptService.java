package com.volkswagen.quizportal.service;

import com.volkswagen.quizportal.exception.EmptyList;
import com.volkswagen.quizportal.model.QuizPortalQuestion;
import com.volkswagen.quizportal.payload.QuizPortalAttemptRequestDTO;
import com.volkswagen.quizportal.payload.QuizPortalAttemptResponseDTO;
import com.volkswagen.quizportal.payload.QuizPortalQuestAndAnswer;

import java.util.List;
import java.util.Set;

public interface QuizPortalAttemptService {

    QuizPortalAttemptResponseDTO saveAttemptedQuiz(QuizPortalAttemptRequestDTO attemptRequestDTO) throws EmptyList;

    int calculateQuizMark(List<QuizPortalQuestAndAnswer> questAndAnswers, Set<QuizPortalQuestion> quizPortalQuestions, Integer marksPerQuestion) throws EmptyList;

}
