package com.volkswagen.quizportal.controller;

import com.volkswagen.quizportal.exception.EmptyList;
import com.volkswagen.quizportal.exception.QuestionCountZero;
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
import java.util.Set;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1")
public class QuizPortalQuestionController {

    @Autowired
    private QuizPortalQuestionServiceImpl questionService;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/questions/topic/{topicId}")
    public  ResponseEntity<List<QuizPortalQuestion>> saveListOfQuestion(@PathVariable Integer topicId, @RequestBody List<QuizPortalQuestion> listOfQuestion) {
        List<QuizPortalQuestion> response;
        try{
            response = questionService.saveListOfQuestion(listOfQuestion,topicId);
        }catch (TopicNotFound topicNotFound) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, topicNotFound.getMessage(), topicNotFound);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/questions/topic/{topicId}")
    public ResponseEntity<Set<QuizPortalQuestion>> getListOfQuestionBasedOnTopicId(@PathVariable Integer topicId) {
        Set<QuizPortalQuestion> response;
        try{
            response = questionService.questionListBasedOnTopicId(topicId);
        } catch (TopicNotFound topicNotFound) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, topicNotFound.getMessage(), topicNotFound);
        } catch (QuestionCountZero questionCountZero) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, questionCountZero.getMessage(), questionCountZero);
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
