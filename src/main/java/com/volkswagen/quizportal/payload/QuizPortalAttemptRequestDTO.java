package com.volkswagen.quizportal.payload;

import java.util.List;

public record QuizPortalAttemptRequestDTO(
        String topicId,
        List<QuizPortalQuestAndAnswer> questCorrectOpt
) {
}
