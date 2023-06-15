package com.volkswagen.quizportal.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record QuizPortalTopicRequestDTO(
        @NotEmpty(message = "Topic name must not be empty")
        @Size(min = 2, message = "Topic name must be 2 character long")
        String topicName,
        @NotEmpty(message = "Topic name must not be empty")
        @Size(min = 2, message = "Topic name must be 2 character long")
        String description,
        Integer numberOfQuestion,
        Integer marksPerQuestion,
        Integer userId
        ) {

}
