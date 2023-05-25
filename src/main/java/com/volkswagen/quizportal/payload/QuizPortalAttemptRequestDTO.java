package com.volkswagen.quizportal.payload;

import java.util.List;

public record QuizPortalAttemptRequestDTO(
        Integer topicId,
        List<QuizPortalQuestAndAnswer> questCorrectOpt
) {
}
