package com.volkswagen.quizportal.payload;

import java.time.LocalDate;

public record QuizPortalTopicResponseDTO(
        Integer topicId,
        String topicName,
        String description,
        Integer numberOfQuestion,
        Integer marksPerQuestion,
        Integer maxMarks,
        Integer publish, // 0 not-publish, 1 published
        LocalDate publishedOn,
        QuizPortalUserDetailsDTO createdBy
) {
}
