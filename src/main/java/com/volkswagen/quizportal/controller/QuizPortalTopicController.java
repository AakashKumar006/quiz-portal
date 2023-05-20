package com.volkswagen.quizportal.controller;

import com.volkswagen.quizportal.exception.EmptyList;
import com.volkswagen.quizportal.exception.UserNotExists;
import com.volkswagen.quizportal.model.QuizPortalTopic;
import com.volkswagen.quizportal.payload.QuizPortalTopicRequestDTO;
import com.volkswagen.quizportal.payload.QuizPortalTopicResponseDTO;
import com.volkswagen.quizportal.service.impl.QuizPortalTopicServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class QuizPortalTopicController {

    @Autowired
    private QuizPortalTopicServiceImpl quizPortalTopicService;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/initiateQuiz")
    public ResponseEntity<QuizPortalTopicResponseDTO> initiateQuiz(@Valid @RequestBody QuizPortalTopicRequestDTO quizPortalTopicRequestDTO) {
        QuizPortalTopicResponseDTO response;
        try{
            response = quizPortalTopicService.initiateQuiz(quizPortalTopicRequestDTO);
        }catch (UserNotExists userNotExists) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, userNotExists.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/topic")
    public ResponseEntity<List<QuizPortalTopicResponseDTO>> getListOfTopicCreatedBy() {
        List<QuizPortalTopicResponseDTO> response;
        try{
            response = quizPortalTopicService.getListOfTopicCreatedBy();
        } catch (EmptyList emptyList) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, emptyList.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/quiz/publish")
    public ResponseEntity<List<QuizPortalTopicResponseDTO>> getListOfPublishedQuiz() {
        List<QuizPortalTopicResponseDTO> response;
        try{
            response = quizPortalTopicService.getListOfAllPublishedQuiz();
        } catch (EmptyList emptyList) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, emptyList.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
