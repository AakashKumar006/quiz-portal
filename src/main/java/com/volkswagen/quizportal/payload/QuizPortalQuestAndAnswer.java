package com.volkswagen.quizportal.payload;

public record QuizPortalQuestAndAnswer(
        Integer questionId,
        String selectedOption
) {
}
