package com.volkswagen.quizportal.service;

import com.volkswagen.quizportal.exception.EmailAlreadyExists;
import com.volkswagen.quizportal.exception.EmailNotExists;
import com.volkswagen.quizportal.exception.InvalidPassword;
import com.volkswagen.quizportal.model.*;
import com.volkswagen.quizportal.payload.QuizPortalUserLoginDTO;
import com.volkswagen.quizportal.payload.QuizPortalUserRegistrationDTO;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

public interface QuizPortalUserService {

    Optional<QuizPortalUser> quizPortalUserRegistration(QuizPortalUserRegistrationDTO quizPortalUserRegistrationRequest) throws EmailAlreadyExists;

    Map<String, String> quizPortalUserLogin(QuizPortalUserLoginDTO quizPortalUserLoginDto) throws EmailNotExists, InvalidPassword;

    List<QuizPortalUser> getListOfAllUser();

    static int calculateAge(LocalDate birthDate) {
        LocalDate now = LocalDate.now();
        if ((birthDate != null)) {
            return Period.between(birthDate, now).getYears();
        } else {
            return 0;
        }
    }
}
