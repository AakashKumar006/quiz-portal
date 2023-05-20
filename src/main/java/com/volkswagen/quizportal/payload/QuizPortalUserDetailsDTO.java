package com.volkswagen.quizportal.payload;

import java.time.LocalDate;

public record QuizPortalUserDetailsDTO(
        Integer userId,
        String userFirstName,
        String userMiddleName,
        String userLastName,
        String userEmail,
        Integer userAge,
        LocalDate userDateOfBirth,
        String userPhoneNo,
        String userRole
) {
}
