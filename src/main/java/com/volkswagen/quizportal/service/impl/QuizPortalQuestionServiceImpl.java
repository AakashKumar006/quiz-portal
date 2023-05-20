package com.volkswagen.quizportal.service.impl;

import com.volkswagen.quizportal.exception.TopicNotFound;
import com.volkswagen.quizportal.model.QuizPortalQuestion;
import com.volkswagen.quizportal.model.QuizPortalTopic;
import com.volkswagen.quizportal.repository.QuizPortalQuestionRepository;
import com.volkswagen.quizportal.repository.QuizPortalTopicRepository;
import com.volkswagen.quizportal.service.IQuizPortalQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizPortalQuestionServiceImpl implements IQuizPortalQuestionService {

    @Autowired
    private QuizPortalTopicRepository topicRepository;

    @Autowired
    private QuizPortalQuestionRepository questionRepository;

    @Override
    public List<QuizPortalQuestion> saveListOfQuestion(QuizPortalQuestion[] quizPortalQuestions, Integer topicId) throws TopicNotFound {
        Optional<QuizPortalTopic> topic = topicRepository.findById(topicId);
        if(topic.isEmpty()) {
            throw new TopicNotFound("Topic Not Found");
        }
       /* List<QuizPortalQuestion> listOfQuestion = new ArrayList<>();
        listOfQuestion.addAll(Arrays.asList(quizPortalQuestions));
        System.out.println("1");*/
        List<QuizPortalQuestion> finalList = new ArrayList<>();
        for(QuizPortalQuestion listOfQuestion : quizPortalQuestions){
            listOfQuestion.setTopic(topic.get());
            finalList.add(listOfQuestion);
        }
        return questionRepository.saveAll(finalList);
    }

    @Override
    public List<QuizPortalQuestion> questionListBasedOnTopicId(Integer topicId) throws TopicNotFound {
        Optional<QuizPortalTopic> listOfTopic = topicRepository.findByTopicId(topicId);
        if(listOfTopic.isEmpty()) {
            throw new TopicNotFound("Topic Not Found");
        }
        List<QuizPortalQuestion> finalList = new ArrayList<>();
        for(QuizPortalQuestion listOfQuestion : listOfTopic.get().getQuestion()){
            finalList.add(listOfQuestion);
        }
        return finalList;
    }
}
