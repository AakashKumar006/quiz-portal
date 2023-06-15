package com.volkswagen.quizportal.service.impl;

import com.volkswagen.quizportal.exception.QuestionCountZero;
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
import java.util.Set;

@Service
public class QuizPortalQuestionServiceImpl implements IQuizPortalQuestionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuizPortalQuestionServiceImpl.class);

    @Autowired
    private QuizPortalTopicRepository topicRepository;

    @Autowired
    private QuizPortalQuestionRepository questionRepository;

    @Override
    public List<QuizPortalQuestion> saveListOfQuestion(List<QuizPortalQuestion> quizPortalQuestions, Integer topicId) throws TopicNotFound {
        Optional<QuizPortalTopic> topic = topicRepository.findById(topicId);
        if(topic.isEmpty()) {
            LOGGER.error("Topic Not Found For Id : {}",topicId);
            throw new TopicNotFound("Topic Not Found");
        }
        /**
         *  initializing ArrayList for QuizPortalQuestion,
         *  adding topic object before setting topic for each question,
         *  saving all to DB
         * */
        List<QuizPortalQuestion> finalList = new ArrayList<>();
        quizPortalQuestions.stream().map(question -> {
            question.setTopic(topic.get());
            finalList.add(question);
            return finalList;
        }).toList();
        return questionRepository.saveAll(finalList);
    }

    @Override
    public Set<QuizPortalQuestion> questionListBasedOnTopicId(Integer topicId) throws TopicNotFound, QuestionCountZero {
        Optional<QuizPortalTopic> topic = topicRepository.findByTopicId(topicId);
        if(topic.isEmpty()) {
            throw new TopicNotFound("Topic Not Exists with id : {}"+topicId);
        }
        if(topic.get().getQuestion().isEmpty()) {
            throw new QuestionCountZero("No Question Associate With Topic Id : "+topicId);
        }
        return topic.get().getQuestion();
    }

    @Override
    public QuizPortalQuestion deleteQuestion(Integer questionId) throws QuestionNotFound {
        Optional<QuizPortalQuestion> question = questionRepository.findById(questionId);
        if(question.isEmpty()) {
            LOGGER.error("Question Not Exist with the Id : {}",questionId);
            throw new QuestionNotFound("Question Not Exist with the Id : "+questionId);
        }
        questionRepository.delete(question.get());
        LOGGER.info("Question delete for Id : {}",questionId);
        return question.get();
    }

    @Override
    public QuizPortalQuestion updateQuestion(Integer questionId, QuizPortalQuestion question) throws QuestionNotFound {
        Optional<QuizPortalQuestion> currentQuestion = questionRepository.findById(questionId);
        if(currentQuestion.isEmpty()) {
            LOGGER.error("Question Not Exist with the Id : {}",question.getQuestionId());
            throw new QuestionNotFound("Question Not Exist with the Id : "+question.getQuestionId());
        }
        System.out.println(question.getQuestion());
        currentQuestion.get().setQuestion(question.getQuestion());
        currentQuestion.get().setOptionA(question.getOptionA());
        currentQuestion.get().setOptionB(question.getOptionB());
        currentQuestion.get().setOptionC(question.getOptionC());
        currentQuestion.get().setOptionD(question.getOptionD());
        currentQuestion.get().setCorrectOption(question.getCorrectOption());
        return questionRepository.save(currentQuestion.get());
    }
}
