package com.volkswagen.quizportal.service.impl;

import com.volkswagen.quizportal.exception.EmptyList;
import com.volkswagen.quizportal.exception.UserNotExists;
import com.volkswagen.quizportal.model.QuizPortalTopic;
import com.volkswagen.quizportal.model.QuizPortalUser;
import com.volkswagen.quizportal.payload.QuizPortalTopicDTOMapper;
import com.volkswagen.quizportal.payload.QuizPortalTopicRequestDTO;
import com.volkswagen.quizportal.payload.QuizPortalTopicResponseDTO;
import com.volkswagen.quizportal.repository.QuizPortalTopicRepository;
import com.volkswagen.quizportal.repository.QuizPortalUserRepository;
import com.volkswagen.quizportal.service.IQuizPortalTopicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuizPortalTopicServiceImpl implements IQuizPortalTopicService {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuizPortalTopicServiceImpl.class);

    @Autowired
    private QuizPortalTopicRepository quizPortalTopicRepository;

    @Autowired
    private QuizPortalUserRepository QuizPortalUserRepository;

    @Autowired
    private QuizPortalTopicDTOMapper quizPortalTopicDTOMapper;

    @Override
    public QuizPortalTopicResponseDTO initiateQuiz(QuizPortalTopicRequestDTO topicRequestDTO) throws UserNotExists {
        Optional<QuizPortalUser> topicCreatedBy = QuizPortalUserRepository.findById(topicRequestDTO.userId());
        if(topicCreatedBy.isEmpty()){
            LOGGER.error("user not exist");
            throw new UserNotExists("user not exist");
        }
        /**
         *  converting TopicRequestDTO to TopicEntity and then saving to DB
        */
        QuizPortalTopic topicDetails = new QuizPortalTopic(topicRequestDTO);
        //  setting derived field, before persisting to DB
        topicDetails.setCreatedBy(topicCreatedBy.get());
        topicDetails.setMaxMarks(maxMarksCalculation(topicRequestDTO.numberOfQuestion(),topicRequestDTO.marksPerQuestion()));
        quizPortalTopicRepository.save(topicDetails);
        LOGGER.info("Topic details saved");
        return quizPortalTopicDTOMapper.apply(topicDetails);
    }

    @Override
    public List<QuizPortalTopicResponseDTO> getListOfTopicCreatedBy() throws EmptyList {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<QuizPortalUser> quizPortalUser = QuizPortalUserRepository.findByUserEmail(authentication.getName());
        List<QuizPortalTopic> listOfTopicBasedOnCreatedBy = quizPortalTopicRepository.findByCreatedBy(quizPortalUser.get());
        if(listOfTopicBasedOnCreatedBy.isEmpty()){
            LOGGER.error("topic list is empty");
            throw new EmptyList("topic List is empty");
        }
        return listOfTopicBasedOnCreatedBy.stream().map(quizPortalTopicDTOMapper).collect(Collectors.toList());
    }

    @Override
    public List<QuizPortalTopicResponseDTO> getListOfAllPublishedQuiz() throws EmptyList {
        List<QuizPortalTopic> getListOfAllPublishedQuiz = quizPortalTopicRepository.findByPublish(1);
        if(getListOfAllPublishedQuiz.isEmpty()) {
            throw new EmptyList("no published quiz available");
        }
        return getListOfAllPublishedQuiz.stream().map(quizPortalTopicDTOMapper).collect(Collectors.toList());
    }

    @Override
    public Integer maxMarksCalculation(Integer numberOfQuestion, Integer marksPerQuestion) {
        return numberOfQuestion*marksPerQuestion;
    }
}

