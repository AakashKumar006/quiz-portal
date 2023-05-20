package com.volkswagen.quizportal.payload;

import com.volkswagen.quizportal.model.QuizPortalTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class QuizPortalTopicDTOMapper implements Function<QuizPortalTopic, QuizPortalTopicResponseDTO> {

    @Autowired
    private QuizPortalUserDetailsDTOMapper userDetailsDTOMapper;



    @Override
    public QuizPortalTopicResponseDTO apply(QuizPortalTopic topic) {

        // associating user details with topic
        QuizPortalUserDetailsDTO userDetailsDTO = userDetailsDTOMapper.apply(topic.getCreatedBy());


        return  new QuizPortalTopicResponseDTO(
                topic.getTopicId(),
                topic.getTopicName(),
                topic.getDescription(),
                topic.getNumberOfQuestion(),
                topic.getMarksPerQuestion(),
                topic.getMaxMarks(),
                topic.getPublish(),
                userDetailsDTO
        );
    }
}
