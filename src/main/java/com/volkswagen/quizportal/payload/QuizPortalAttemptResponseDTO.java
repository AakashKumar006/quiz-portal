package com.volkswagen.quizportal.payload;

public record QuizPortalAttemptResponseDTO(
        String topicName,
        Integer marksObtained,
        Integer maxMarks
) {
}
