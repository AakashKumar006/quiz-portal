package com.volkswagen.quizportal.service.impl;

import com.volkswagen.quizportal.exception.EmptyList;
import com.volkswagen.quizportal.exception.QuestionCountZero;
import com.volkswagen.quizportal.exception.TopicNotFound;
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
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuizPortalTopicServiceImpl implements IQuizPortalTopicService {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuizPortalTopicServiceImpl.class);

    @Autowired
    private QuizPortalTopicRepository topicRepository;

    @Autowired
    private QuizPortalUserRepository quizPortalUserRepository;

    @Autowired
    private QuizPortalTopicDTOMapper quizPortalTopicDTOMapper;

    @Override
    public QuizPortalTopicResponseDTO initiateQuiz(QuizPortalTopicRequestDTO topicRequestDTO) throws UserNotExists {
        Optional<QuizPortalUser> topicCreatedBy = quizPortalUserRepository.findById(topicRequestDTO.userId());
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
        topicRepository.save(topicDetails);
        LOGGER.info("Topic details saved");
        return quizPortalTopicDTOMapper.apply(topicDetails);
    }

    @Override
    public List<QuizPortalTopicResponseDTO> getListOfTopicCreatedBy() throws EmptyList {
        QuizPortalUser currUser = (QuizPortalUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<QuizPortalTopic> listOfTopicBasedOnCreatedBy = topicRepository.findByCreatedBy(currUser);
        if(listOfTopicBasedOnCreatedBy.isEmpty()) {
            LOGGER.error("Topic List Is Empty For User");
            throw new EmptyList("Topic List Is Empty For User");
        }
        return listOfTopicBasedOnCreatedBy.stream().map(quizPortalTopicDTOMapper).collect(Collectors.toList());
    }

    @Override
    public List<QuizPortalTopicResponseDTO> getListOfTopicBasedOnPublishStatus(Integer publishStatus) throws EmptyList {
        List<QuizPortalTopic> topicList;
        topicList = topicRepository.findByPublish(publishStatus);
        if(topicList.isEmpty()) {
            if(publishStatus == 0) {
                LOGGER.error("Un-Published Topic List Is Empty");
                throw new EmptyList("Un-Published Topic List Is Empty");
            } else {
                LOGGER.error("Published Topic List Is Empty");
                throw new EmptyList("Published Topic List Is Empty");
            }
        }
        return topicList.stream().map(quizPortalTopicDTOMapper).collect(Collectors.toList());
    }

    @Override
    public List<QuizPortalTopicResponseDTO> getListOfAllTopics() throws EmptyList {
        List<QuizPortalTopic> topics = topicRepository.findAll();
        if(topics.isEmpty()) {
            throw new EmptyList("Topic List Is Empty");
        }
        return topics.stream().map(quizPortalTopicDTOMapper).collect(Collectors.toList());
    }

    @Override
    public boolean publishQuiz(Integer topicId) throws QuestionCountZero, EmptyList {
        /**
         *  at least one question must be associated with the topic to publish the quiz.
         */
        Optional<QuizPortalTopic> topic = topicRepository.findByTopicId(topicId);
        if(topic.isEmpty()) {
            LOGGER.error("No topic exists with id : "+topicId);
            throw new EmptyList("Topic Is Not Present with id "+topicId);
        }
        if(topic.get().getQuestion().size() == 0) {
            LOGGER.error("No Question Is Associated With Topic Id : "+topicId);
            throw new QuestionCountZero("No Question is Associated with topic id : "+topicId);
        }
        /**
         *  updating publish status to 1 (published)
         * */
        topic.get().setPublish(1);
        topic.get().setPublishedOn(LocalDate.now());
        topicRepository.save(topic.get());
        LOGGER.info("Quiz Published for topic id: "+topicId);
        return true;
    }

    @Override
    public QuizPortalTopic deleteTopic(Integer topicId) throws TopicNotFound {
        Optional<QuizPortalTopic> topic = topicRepository.findByTopicId(topicId);
        if(topic.isEmpty()) {
            throw new TopicNotFound("Topic Not Exist For Id : "+topicId);
        }
        topicRepository.delete(topic.get());
        LOGGER.info("Topic Deleted For Id : "+topicId);
        return topic.get();
    }

    @Override
    public QuizPortalTopic updateTopic(Integer topicId, QuizPortalTopic topic) throws TopicNotFound {
        Optional<QuizPortalTopic> currTopic = topicRepository.findByTopicId(topicId);
        if(currTopic.isEmpty()) {
            throw new TopicNotFound("Topic Not Exist For Id : "+topicId);
        }
        topicRepository.save(topic);
        LOGGER.info("Topic Update For Id : "+topicId);
        return topic;
    }
}

