package com.volkswagen.quizportal.payload;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class QuizPortalUserLoginDTO {
    private String userEmail;
    private String userPassword;
}
