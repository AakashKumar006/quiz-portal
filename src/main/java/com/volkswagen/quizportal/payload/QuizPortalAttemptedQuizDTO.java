package com.volkswagen.quizportal.payload;

public record QuizPortalAttemptedQuizDTO(
        String topicId,
        String questionId,
        String selectedOption
) {
}
