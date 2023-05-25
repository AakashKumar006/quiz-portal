package com.volkswagen.quizportal.controller;

import com.volkswagen.quizportal.exception.EmptyList;
import com.volkswagen.quizportal.exception.QuestionNotFound;
import com.volkswagen.quizportal.exception.TopicNotFound;
import com.volkswagen.quizportal.model.QuizPortalQuestion;
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
    public  ResponseEntity<List<QuizPortalQuestion>> saveListOfQuestion(@PathVariable String id, @RequestBody List<QuizPortalQuestion> question) {
        List<QuizPortalQuestion> response;
        try{
            response = questionService.saveListOfQuestion(question,Integer.parseInt(id));
        }catch (TopicNotFound topicNotFound) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, topicNotFound.getMessage(), topicNotFound);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/question/all/{topicId}")
    public ResponseEntity<List<QuizPortalQuestion>> getListOfQuestionBasedOnTopicId(@PathVariable Integer topicId) {
        List<QuizPortalQuestion> response;
        try{
            response = questionService.questionListBasedOnTopicId(topicId);
        }catch(EmptyList topicNotFound) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, topicNotFound.getMessage(), topicNotFound);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/questions/{id}")
    public ResponseEntity<QuizPortalQuestion> deleteQuestion(@PathVariable("id") Integer questionId) {
        QuizPortalQuestion response;
        try {
            response = questionService.deleteQuestion(questionId);
        } catch (QuestionNotFound questionNotFound) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, questionNotFound.getMessage(), questionNotFound);
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/questions/{id}")
    public ResponseEntity<QuizPortalQuestion> updateQuestion(@PathVariable("id") Integer questionId, @RequestBody QuizPortalQuestion question) {
        QuizPortalQuestion response;
        try {
            response = questionService.updateQuestion(questionId, question);
        } catch (QuestionNotFound questionNotFound) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, questionNotFound.getMessage(), questionNotFound);
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
