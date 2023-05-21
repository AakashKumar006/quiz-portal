package com.volkswagen.quizportal.payload;

public record QuizPortalQuizEvaluationDTO(
        Integer question_id,
        String correct_option
) {
}
