package com.volkswagen.quizportal.payload;

import com.volkswagen.quizportal.model.QuizPortalAttempt;
import com.volkswagen.quizportal.service.QuizPortalAttemptService;
import org.springframework.stereotype.Service;
import java.util.function.Function;

@Service
public class QuizPortalAttemptResponseDTOMapper implements Function<QuizPortalAttempt, QuizPortalAttemptResponseDTO> {

    @Override
    public QuizPortalAttemptResponseDTO apply(QuizPortalAttempt quizPortalAttempt) {
    /**
     * using static method in QuizPortalAttemptService
     * To calculate maxMarks for Quiz
     * */
    return new QuizPortalAttemptResponseDTO(
            quizPortalAttempt.getTopic().getTopicName(),
            quizPortalAttempt.getTopic().getQuestion().size(),
            quizPortalAttempt.getTopic().getMarksPerQuestion(),
            QuizPortalAttemptService.calculateMaxMarks(quizPortalAttempt.getTopic().getQuestion().size(),quizPortalAttempt.getTopic().getMarksPerQuestion()),
            quizPortalAttempt.getMarksObtained(),
            quizPortalAttempt.getAttemptedOn());
    }
}
