package com.volkswagen.quizportal.controller;

import com.volkswagen.quizportal.exception.EmptyList;
import com.volkswagen.quizportal.exception.TopicNotFound;
import com.volkswagen.quizportal.exception.UserNotExists;
import com.volkswagen.quizportal.model.QuizPortalTopic;
import com.volkswagen.quizportal.payload.QuizPortalTopicRequestDTO;
import com.volkswagen.quizportal.payload.QuizPortalTopicResponseDTO;
import com.volkswagen.quizportal.service.impl.QuizPortalTopicServiceImpl;
import jakarta.persistence.criteria.CriteriaBuilder;
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
@RequestMapping("/api/v1")
public class QuizPortalTopicController {

    @Autowired
    private QuizPortalTopicServiceImpl quizPortalTopicService;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/topic")
    public ResponseEntity<QuizPortalTopicResponseDTO> initiateQuiz(@Valid @RequestBody QuizPortalTopicRequestDTO quizPortalTopicRequestDTO) {
        QuizPortalTopicResponseDTO response;
        try{
            response = quizPortalTopicService.initiateQuiz(quizPortalTopicRequestDTO);
        }catch (UserNotExists userNotExists) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, userNotExists.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/topics/currUser")
    public ResponseEntity<List<QuizPortalTopicResponseDTO>> getListOfTopicCreatedBy() {
        List<QuizPortalTopicResponseDTO> response;
        try{
            response = quizPortalTopicService.getListOfTopicCreatedBy();
        } catch (EmptyList emptyList) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, emptyList.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/topics")
    public ResponseEntity<List<QuizPortalTopicResponseDTO>> getListOfTopic(@RequestParam(value = "publish", required = false) Integer publishStatus) {
        List<QuizPortalTopicResponseDTO> response;
        try{
            if(publishStatus == null) {
                response = quizPortalTopicService.getListOfAllTopics();
            } else {
                response = quizPortalTopicService.getListOfTopicBasedOnPublishStatus(publishStatus);
            }
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PatchMapping ("/topic/{topicId}/publish")
    public ResponseEntity<String> publishQuiz(@PathVariable("topicId") Integer topicId) {
        try {
            quizPortalTopicService.publishQuiz(topicId);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/topic/{topicId}")
    public ResponseEntity<QuizPortalTopic> deleteTopic(@PathVariable("topicId") Integer topicId) {
        QuizPortalTopic response;
        try{
            response = quizPortalTopicService.deleteTopic(topicId);
        } catch(TopicNotFound topicNotFound) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, topicNotFound.getMessage(), topicNotFound);
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/topic/{topicId}")
    public ResponseEntity<QuizPortalTopic> updateTopic(@PathVariable("topicId") Integer topicId, @RequestBody QuizPortalTopic topic) {
        QuizPortalTopic response;
        try{
            response = quizPortalTopicService.updateTopic(topicId, topic);
        }catch (TopicNotFound topicNotFound) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, topicNotFound.getMessage(), topicNotFound);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
