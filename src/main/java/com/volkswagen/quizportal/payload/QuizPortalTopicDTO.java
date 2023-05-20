package com.volkswagen.quizportal.payload;

import com.volkswagen.quizportal.model.QuizPortalUser;
import java.time.LocalDate;

public record QuizPortalTopicDTO(
        String topicName,
        String description,
        Integer numberOfQuestion,
        Integer marksPerQuestion,
        Integer maxMarks,
        QuizPortalUser createdBy,
        LocalDate createdOn,
        Integer publish){

}
