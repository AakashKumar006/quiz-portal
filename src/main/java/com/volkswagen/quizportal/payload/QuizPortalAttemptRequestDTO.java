package com.volkswagen.quizportal.payload;

import java.util.Set;

public record QuizPortalAttemptRequestDTO(
        Integer topicId,
        Set<QuizPortalQuestAndAnswer> questCorrectOpt
) {
}
