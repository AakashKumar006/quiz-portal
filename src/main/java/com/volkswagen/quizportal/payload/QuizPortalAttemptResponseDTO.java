package com.volkswagen.quizportal.payload;

import java.time.LocalDate;

public record QuizPortalAttemptResponseDTO(
        String topicName,
        Integer numberOfQuestion,
        Integer marksPerQuestion,
        Integer maxMarks,
        Integer marksObtained,
        LocalDate attemptedOn
) {
}
