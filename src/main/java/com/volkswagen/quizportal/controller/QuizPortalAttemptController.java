package com.volkswagen.quizportal.controller;

import com.volkswagen.quizportal.exception.EmptyList;
import com.volkswagen.quizportal.payload.QuizPortalAttemptRequestDTO;
import com.volkswagen.quizportal.service.impl.QuizPortalAttemptServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/attempts")
public class QuizPortalAttemptController {

    @Autowired
    private QuizPortalAttemptServiceImpl quizPortalAttemptService;

    @PostMapping
    public void saveUserQuizAttempt(@RequestBody QuizPortalAttemptRequestDTO question) {
        try {
            quizPortalAttemptService.saveAttemptedQuiz(question);
        } catch (EmptyList emptyList) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, emptyList.getMessage(), emptyList);
        }

        return;
    }

    @GetMapping
    public void getListOfQuizAttempts(@RequestBody QuizPortalAttemptRequestDTO question) {
        System.out.println(question);
        return;
    }



}
