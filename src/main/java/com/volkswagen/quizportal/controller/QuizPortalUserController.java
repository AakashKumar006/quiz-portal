package com.volkswagen.quizportal.controller;

import com.volkswagen.quizportal.exception.EmailAlreadyExists;
import com.volkswagen.quizportal.model.*;
import com.volkswagen.quizportal.payload.QuizPortalUserLoginDTO;
import com.volkswagen.quizportal.payload.QuizPortalUserRegistrationDTO;
import com.volkswagen.quizportal.service.impl.QuizPortalUserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import java.util.*;

@RestController
@CrossOrigin(origins = "*")
@EnableWebMvc
public class QuizPortalUserController {

    private QuizPortalUserServiceImpl quizPortalUserServiceImpl;

    public QuizPortalUserController(QuizPortalUserServiceImpl quizPortalUserServiceImpl) {
        this.quizPortalUserServiceImpl = quizPortalUserServiceImpl;
    }

    @PostMapping("/registration")
    public ResponseEntity<Optional<QuizPortalUser>> userRegistration(@Valid @RequestBody QuizPortalUserRegistrationDTO quizPortalUserRegistrationDto) {
        Optional<QuizPortalUser> quizPortalUser;
        try{
            quizPortalUser = quizPortalUserServiceImpl.quizPortalUserRegistration(quizPortalUserRegistrationDto);
        } catch (EmailAlreadyExists emailAlreadyExists) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, emailAlreadyExists.getMessage(),emailAlreadyExists);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(quizPortalUser);
    }

    @PostMapping("/userLogin")
    public ResponseEntity<Map<String,String>> userLogin(@RequestBody QuizPortalUserLoginDTO quizPortalUserLoginDto) {
        Map<String,String> userResponse;
        try{
            userResponse = quizPortalUserServiceImpl.quizPortalUserLogin(quizPortalUserLoginDto);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        }
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/user/all")
    public ResponseEntity<List<QuizPortalUser>> getListOfAllUsers() {
        return new ResponseEntity<>(quizPortalUserServiceImpl.getListOfAllUser(),HttpStatus.OK);
    }

    @GetMapping("/")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String testing() {
        return "hello";
    }


}
