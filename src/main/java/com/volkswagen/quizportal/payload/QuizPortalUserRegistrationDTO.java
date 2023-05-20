package com.volkswagen.quizportal.payload;

import com.volkswagen.quizportal.validate.PasswordValid;
import com.volkswagen.quizportal.validate.PhoneNoValid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@ToString
@Getter
@Builder
@AllArgsConstructor
public class QuizPortalUserRegistrationDTO {

    @NotEmpty(message = "user name must not be empty")
    @Size(min = 2, message = "user name must be 2 character long")
    private String userFirstName;

    private String userMiddleName;

    @NotEmpty(message = "user name must not be empty")
    @Size(min = 2, message = "user name must be 2 character long")
    private String userLastName;

    private String userDateOfBirth;

    @Email(message = "Invalid email")
    private String userEmail;

    @PhoneNoValid
    private String userPhoneNo;

    @PasswordValid
    private String userPassword;
}
