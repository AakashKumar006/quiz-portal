package com.volkswagen.quizportal.controller;

import com.volkswagen.quizportal.exception.TopicNotFound;
import com.volkswagen.quizportal.model.QuizPortalQuestion;
import com.volkswagen.quizportal.payload.QuizPortalAttemptedQuizDTO;
import com.volkswagen.quizportal.service.impl.QuizPortalQuestionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class QuizPortalQuestionController {

    @Autowired
    private QuizPortalQuestionServiceImpl questionService;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/addQuestion/{id}")
    public  ResponseEntity<List<QuizPortalQuestion>> allQuestionAndAnswer(@PathVariable String id, @RequestBody QuizPortalQuestion[] question) {
        List<QuizPortalQuestion> response;
        try{
            response = questionService.saveListOfQuestion(question,Integer.parseInt(id));
        }catch (TopicNotFound topicNotFound) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, topicNotFound.getMessage(), topicNotFound);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/question/all/{topicId}")
    public ResponseEntity<List<QuizPortalQuestion>> getListOfQuestionBasedOnTopicId(@PathVariable String topicId) {
        List<QuizPortalQuestion> response;
        try{
            System.out.println("inside the question");
            response = questionService.questionListBasedOnTopicId(Integer.parseInt(topicId));
        }catch(TopicNotFound topicNotFound) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, topicNotFound.getMessage(), topicNotFound);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @PostMapping("/quiz/attempted/{id}")
    public String attemptedQuizData(@PathVariable String id, @RequestBody QuizPortalAttemptedQuizDTO[] question) {
        System.out.println("topicId : "+id);
       for(QuizPortalAttemptedQuizDTO data : question){
           System.out.println("questionId : "+data.questionId());
           System.out.println("selected option : "+data.selectedOption());
           System.out.println("@@");
       }

        return "";
    }

}
