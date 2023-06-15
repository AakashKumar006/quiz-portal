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
import com.volkswagen.quizportal.service.QuizPortalAttemptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class QuizPortalAttemptServiceImpl implements QuizPortalAttemptService {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuizPortalAttemptServiceImpl.class);

    @Autowired
    private QuizPortalTopicRepository quizPortalTopicRepository;

    @Autowired
    private QuizPortalAttemptRepository attemptRepository;

    @Autowired
    private QuizPortalAttemptResponseDTOMapper attemptResponseDTOMapper;

    @Override
    public QuizPortalAttemptResponseDTO saveAttemptedQuiz(QuizPortalAttemptRequestDTO attemptRequestDTO) throws EmptyList, UserNotExists {
        QuizPortalUser user = (QuizPortalUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<QuizPortalTopic>  topic = quizPortalTopicRepository.findByTopicId(attemptRequestDTO.topicId());
        if(topic.isEmpty()) {
            LOGGER.error("topic is not present with id {}",attemptRequestDTO.topicId());
            throw new EmptyList("topic is not present with id"+attemptRequestDTO.topicId());
        }
        /**
         * Creating the object of QuizPortalAttempt entity,
         * setting the fields
         * saving into db
         * mapping to quizPortalAttemptResponseDTO and returning to client
         * */
        Integer marks = calculateQuizMark(attemptRequestDTO.questCorrectOpt(),topic.get().getQuestion(), topic.get().getMarksPerQuestion());
        QuizPortalAttempt quizPortalAttempt = new QuizPortalAttempt(topic.get(),user,marks);
        QuizPortalAttempt savedAttemptData = attemptRepository.save(quizPortalAttempt);
        return attemptResponseDTOMapper.apply(savedAttemptData);
    }

    @Override
    public int calculateQuizMark(Set<QuizPortalQuestAndAnswer> questAndAnswers, Set<QuizPortalQuestion> quizPortalQuestion, Integer marksPerQuestion) throws EmptyList {
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
                .toList().size()*marksPerQuestion;
    }

    @Override
    public List<QuizPortalAttemptResponseDTO> getListOfAttemptsBasedOnLoggedInUser() throws EmptyList {
        Optional<List<QuizPortalAttempt>> listOfQuizAttempts = attemptRepository.findByAttemptedBy((QuizPortalUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        if(listOfQuizAttempts.isEmpty()) {
            LOGGER.error("No quiz attempted by Logged In User");
            throw new EmptyList("No quiz attempted by Logged In User");
        }
        return listOfQuizAttempts.get().stream().map(attemptResponseDTOMapper).toList();
    }
}
