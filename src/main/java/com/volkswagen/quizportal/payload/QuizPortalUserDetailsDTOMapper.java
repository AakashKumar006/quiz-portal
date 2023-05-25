package com.volkswagen.quizportal.payload;

import com.volkswagen.quizportal.model.QuizPortalTopic;
import com.volkswagen.quizportal.model.QuizPortalUser;
import com.volkswagen.quizportal.model.QuizPortalUserRoleMap;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class QuizPortalUserDetailsDTOMapper implements Function<QuizPortalUser, QuizPortalUserDetailsDTO> {
    @Override
    public QuizPortalUserDetailsDTO apply(QuizPortalUser user) {
        String roleName = "";
        for(QuizPortalUserRoleMap role : user.getUserRoleMap()){
            roleName = role.getRoles().getRoleName();
        }
        return  new QuizPortalUserDetailsDTO(
                user.getUserId(),
                user.getUserFirstName(),
                user.getUserMiddleName(),
                user.getUserLastName(),
                user.getUserEmail(),
                user.getUserAge(),
                user.getUserDateOfBirth(),
                user.getUserPhoneNo(),
                roleName,
                user.getIsActive()
        );
    }


}
