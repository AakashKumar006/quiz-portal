package com.volkswagen.quizportal.controller;

import com.volkswagen.quizportal.exception.EmptyList;
import com.volkswagen.quizportal.exception.UserNotExists;
import com.volkswagen.quizportal.payload.QuizPortalAttemptRequestDTO;
import com.volkswagen.quizportal.payload.QuizPortalAttemptResponseDTO;
import com.volkswagen.quizportal.service.impl.QuizPortalAttemptServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1")
public class QuizPortalAttemptController {

    @Autowired
    private QuizPortalAttemptServiceImpl quizPortalAttemptService;

    @PostMapping("/attempt")
    public ResponseEntity<QuizPortalAttemptResponseDTO> saveUserQuizAttempt(@RequestBody QuizPortalAttemptRequestDTO question) {
        QuizPortalAttemptResponseDTO responseDTO;
        try {
            responseDTO = quizPortalAttemptService.saveAttemptedQuiz(question);
        } catch (UserNotExists userNotExists) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, userNotExists.getMessage(), userNotExists);
        } catch (EmptyList emptyList) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, emptyList.getMessage(), emptyList);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping("/attempts")
    public void getListOfAllAttempts() {
        return;
    }

    @GetMapping("/attempts/user/{id}")
    public ResponseEntity<List<QuizPortalAttemptResponseDTO>> getListOfAttemptsBasedOnUser(@PathVariable("id") Integer userId) {
        List<QuizPortalAttemptResponseDTO> responseDTO;
        try {
            responseDTO = quizPortalAttemptService.getListOfAttemptsBasedOnUserId(userId);
        } catch (UserNotExists userNotExists) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, userNotExists.getMessage(), userNotExists);
        }catch (EmptyList emptyList) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, emptyList.getMessage(), emptyList);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

}
