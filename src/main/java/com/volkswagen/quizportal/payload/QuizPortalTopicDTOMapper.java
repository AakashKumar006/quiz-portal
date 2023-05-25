package com.volkswagen.quizportal.payload;

import com.volkswagen.quizportal.model.QuizPortalTopic;
import com.volkswagen.quizportal.service.IQuizPortalTopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.function.Function;

@Service
public class QuizPortalTopicDTOMapper implements Function<QuizPortalTopic, QuizPortalTopicResponseDTO> {

    @Autowired
    private QuizPortalUserDetailsDTOMapper userDetailsDTOMapper;

    @Override
    public QuizPortalTopicResponseDTO apply(QuizPortalTopic topic) {
        int noOfQuestion = 0;
        int maxMarks = 0;
        if(topic.getQuestion() != null) {
            noOfQuestion = topic.getQuestion().size();
            maxMarks =  IQuizPortalTopicService.maxMarksCalculation(topic.getQuestion().size(),topic.getMarksPerQuestion());
        }

        // associating user details with topic
        QuizPortalUserDetailsDTO userDetailsDTO = userDetailsDTOMapper.apply(topic.getCreatedBy());
        return  new QuizPortalTopicResponseDTO(
                topic.getTopicId(),
                topic.getTopicName(),
                topic.getDescription(),
                noOfQuestion, // number of question
                topic.getMarksPerQuestion(),
                maxMarks, // maxMarks
                topic.getPublish(),
                topic.getPublishedOn(),
                userDetailsDTO
        );
    }
}
