package com.volkswagen.quizportal.service.impl;

import com.volkswagen.quizportal.exception.EmptyList;
import com.volkswagen.quizportal.exception.UserNotExists;
import com.volkswagen.quizportal.model.QuizPortalAttempt;
import com.volkswagen.quizportal.model.QuizPortalQuestion;
import com.volkswagen.quizportal.model.QuizPortalTopic;
import com.volkswagen.quizportal.model.QuizPortalUser;
import com.volkswagen.quizportal.payload.QuizPortalAttemptRequestDTO;
import com.volkswagen.quizportal.payload.QuizPortalAttemptResponseDTO;
import com.volkswagen.quizportal.payload.QuizPortalAttemptResponseDTOMapper;
import com.volkswagen.quizportal.payload.QuizPortalQuestAndAnswer;
import com.volkswagen.quizportal.repository.QuizPortalAttemptRepository;
import com.volkswagen.quizportal.repository.QuizPortalTopicRepository;
import com.volkswagen.quizportal.repository.QuizPortalUserRepository;
import com.volkswagen.quizportal.service.QuizPortalAttemptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class QuizPortalAttemptServiceImpl implements QuizPortalAttemptService {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuizPortalAttemptServiceImpl.class);

    @Autowired
    private QuizPortalTopicRepository quizPortalTopicRepository;

    @Autowired
    private QuizPortalUserRepository userRepository;

    @Autowired
    private QuizPortalAttemptRepository attemptRepository;

    @Autowired
    private QuizPortalAttemptResponseDTOMapper attemptResponseDTOMapper;

    @Override
    public QuizPortalAttemptResponseDTO saveAttemptedQuiz(QuizPortalAttemptRequestDTO attemptRequestDTO) throws EmptyList, UserNotExists {
        Optional<QuizPortalUser> user = userRepository.findByUserEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if(user.isEmpty()) {
            LOGGER.error("user not exists");
            throw new UserNotExists("user not exists");
        }
        Optional<QuizPortalTopic>  topic = quizPortalTopicRepository.findByTopicId(attemptRequestDTO.topicId());
        if(topic.isEmpty()) {
            LOGGER.error("topic is not present with id"+attemptRequestDTO.topicId());
            throw new EmptyList("topic is not present with id"+attemptRequestDTO.topicId());
        }
        /**
         * Creating the object of QuizPortalAttempt entity,
         * setting the fields
         * saving into db
         * mapping to quizPortalAttemptResponseDTO and returning to client
         * */
        Integer marks = calculateQuizMark(attemptRequestDTO.questCorrectOpt(),topic.get().getQuestion(), topic.get().getMarksPerQuestion());
        QuizPortalAttempt quizPortalAttempt = new QuizPortalAttempt(topic.get(),user.get(),marks);
        QuizPortalAttempt savedAttemptData = attemptRepository.save(quizPortalAttempt);
        LOGGER.info("Attempted Quiz saved for user id : "+user.get().getUserId());
        return attemptResponseDTOMapper.apply(savedAttemptData);
    }

    @Override
    public int calculateQuizMark(List<QuizPortalQuestAndAnswer> questAndAnswers, Set<QuizPortalQuestion> quizPortalQuestion, Integer marksPerQuestion) throws EmptyList {
        if(questAndAnswers.isEmpty()) {
            LOGGER.error("question and answer list is empty");
            throw new EmptyList("question and answer list is empty");
        }
        if(quizPortalQuestion.isEmpty()) {
            LOGGER.error("quiz portal question list is empty");
            throw new EmptyList("quiz portal question list is empty");
        }
        return questAndAnswers.stream()
                .filter( user -> quizPortalQuestion.stream().anyMatch(correct -> user.questionId().equals(correct.getQuestionId()) && user.selectedOption().equals(correct.getCorrectOption())))
                .collect(Collectors.toList()).size()*marksPerQuestion;
    }

    @Override
    public List<QuizPortalAttemptResponseDTO> getListOfAttemptsBasedOnUserId(Integer userId) throws UserNotExists, EmptyList {
        Optional<QuizPortalUser> user = userRepository.findById(userId);
        if(user.isEmpty()) {
            LOGGER.error("user not exists");
            throw new UserNotExists("user not exist");
        }
        Optional<List<QuizPortalAttempt>> listOfQuizAttempts = attemptRepository.findByAttemptedBy(user.get());
        if(listOfQuizAttempts.isEmpty()) {
            LOGGER.error("No quiz attempted by user id"+user.get().getUserId());
            throw new EmptyList("No quiz attempted by user id"+user.get().getUserId());
        }
        return listOfQuizAttempts.get().stream().map(attemptResponseDTOMapper).collect(Collectors.toList());
    }
}
