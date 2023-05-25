package com.volkswagen.quizportal.service.impl;

import com.volkswagen.quizportal.exception.EmptyList;
import com.volkswagen.quizportal.exception.QuestionNotFound;
import com.volkswagen.quizportal.exception.TopicNotFound;
import com.volkswagen.quizportal.model.QuizPortalQuestion;
import com.volkswagen.quizportal.model.QuizPortalTopic;
import com.volkswagen.quizportal.repository.QuizPortalQuestionRepository;
import com.volkswagen.quizportal.repository.QuizPortalTopicRepository;
import com.volkswagen.quizportal.service.IQuizPortalQuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuizPortalQuestionServiceImpl implements IQuizPortalQuestionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuizPortalAttemptServiceImpl.class);

    @Autowired
    private QuizPortalTopicRepository topicRepository;

    @Autowired
    private QuizPortalQuestionRepository questionRepository;

    @Override
    public List<QuizPortalQuestion> saveListOfQuestion(List<QuizPortalQuestion> quizPortalQuestions, Integer topicId) throws TopicNotFound {
        Optional<QuizPortalTopic> topic = topicRepository.findById(topicId);
        if(topic.isEmpty()) {
            LOGGER.error("Topic Not Found For Id : "+topicId);
            throw new TopicNotFound("Topic Not Found");
        }
        /**
         *  initializing ArrayList for QuizPortalQuestion,
         *  adding topic object before setting topic for each question,
         *  saving to DB by saveAll
         * */
        List<QuizPortalQuestion> finalList = new ArrayList<>();
        quizPortalQuestions.stream().map(question -> {
            question.setTopic(topic.get());
            finalList.add(question);
            return finalList;
        }).collect(Collectors.toList());
        return questionRepository.saveAll(finalList);
    }

    @Override
    public List<QuizPortalQuestion> questionListBasedOnTopicId(Integer topicId) throws EmptyList {

        Optional<List<QuizPortalQuestion>> listOfQuestions = questionRepository.findListOfQuestionBasedOnTopicId(topicId);
        if(listOfQuestions.get().size() == 0) {
            LOGGER.error("No Question Is Associated With Topic Id : "+topicId);
            throw new EmptyList("No Question Is Associated With Topic Id : "+topicId);
        }
        return listOfQuestions.get();
    }

    @Override
    public QuizPortalQuestion deleteQuestion(Integer questionId) throws QuestionNotFound {
        Optional<QuizPortalQuestion> question = questionRepository.findById(questionId);
        if(question.isEmpty()) {
            LOGGER.error("Question Not Exist with the Id : "+questionId);
            throw new QuestionNotFound("Question Not Exist with the Id : "+questionId);
        }
        /*Cascade DELETE would work if you were deleting the department and wanted all contacts to be deleted. In this case, though, we are just deleting a single contact, and not the entire department. Since the contact is in the department's contact collection, and that collection has SAVE/UPDATE cascading, hibernate complains that the item will be re-saved, which is the error that is given.*/
        questionRepository.delete(question.get());
        LOGGER.info("Question delete for Id : "+questionId);
        return question.get();
    }

    @Override
    public QuizPortalQuestion updateQuestion(Integer questionId, QuizPortalQuestion question) throws QuestionNotFound {
        Optional<QuizPortalQuestion> currentQuestion = questionRepository.findById(questionId);
        if(currentQuestion.isEmpty()) {
            LOGGER.error("Question Not Exist with the Id : "+question.getQuestionId());
            throw new QuestionNotFound("Question Not Exist with the Id : "+question.getQuestionId());
        }
        return questionRepository.save(question);
    }
}
