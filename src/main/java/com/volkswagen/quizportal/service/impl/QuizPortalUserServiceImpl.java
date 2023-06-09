package com.volkswagen.quizportal.service.impl;

import com.volkswagen.quizportal.exception.EmailAlreadyExists;
import com.volkswagen.quizportal.exception.EmailNotExists;
import com.volkswagen.quizportal.exception.InvalidPassword;
import com.volkswagen.quizportal.model.*;
import com.volkswagen.quizportal.payload.QuizPortalUserLoginDTO;
import com.volkswagen.quizportal.payload.QuizPortalUserRegistrationDTO;
import com.volkswagen.quizportal.repository.QuizPortalUserRepository;
import com.volkswagen.quizportal.service.QuizPortalUserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Service
public class QuizPortalUserServiceImpl implements QuizPortalUserService {

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final QuizPortalUserRepository quizPortalUserRepository;
    private final QuizPortalRoleServiceImpl quizPortalRoleServiceImpl;


    public QuizPortalUserServiceImpl(PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, QuizPortalUserRepository quizPortalUserRepository, QuizPortalRoleServiceImpl quizPortalRoleServiceImpl) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.quizPortalUserRepository = quizPortalUserRepository;
        this.quizPortalRoleServiceImpl = quizPortalRoleServiceImpl;

    }

    @Override
    public Optional<QuizPortalUser> quizPortalUserRegistration(QuizPortalUserRegistrationDTO registrationDto) throws  EmailAlreadyExists {
        DateTimeFormatter dataFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Optional<QuizPortalUser> userExist = quizPortalUserRepository.findByUserEmail(registrationDto.getUserEmail());
        if(userExist.isPresent()) {
            throw new EmailAlreadyExists("Email is already exists");
        }
        QuizPortalUser quizPortalUser = new QuizPortalUser();
        quizPortalUser.setUserFirstName(registrationDto.getUserFirstName());
        quizPortalUser.setUserMiddleName(registrationDto.getUserMiddleName());
        quizPortalUser.setUserLastName(quizPortalUser.getUserLastName());
        quizPortalUser.setUserEmail(registrationDto.getUserEmail());
        LocalDate userDateOfBirth = LocalDate.parse(registrationDto.getUserDateOfBirth(),dataFormatter);
        quizPortalUser.setUserDateOfBirth(userDateOfBirth);
        quizPortalUser.setUserPassword(passwordEncoder.encode(registrationDto.getUserPassword()));
        quizPortalUser.setUserCreatedOn(LocalDate.now());
        quizPortalUser.setUserAge(QuizPortalUserService.calculateAge(userDateOfBirth));
        quizPortalUser.setUserPhoneNo(registrationDto.getUserPhoneNo());
        quizPortalUser.setIsActive(1);
        QuizPortalRole role = quizPortalRoleServiceImpl.saveRole(new QuizPortalRole("ROLE_ADMIN","default",1));
        Set<QuizPortalUserRoleMap> userRoleMap = new HashSet<>();
        QuizPortalUserRoleMap roleMap = new QuizPortalUserRoleMap(quizPortalUser,role);
        userRoleMap.add(roleMap);
        quizPortalUser.setUserRoleMap(userRoleMap);
        quizPortalUserRepository.save(quizPortalUser);
        return quizPortalUserRepository.findByUserEmail(registrationDto.getUserEmail());
    }

    @Override
    public Map<String, String> quizPortalUserLogin(QuizPortalUserLoginDTO quizPortalUserLoginDto) throws EmailNotExists, InvalidPassword {
        Map<String,String> userLoginDetails = new HashMap<>();
        Optional<QuizPortalUser> quizPortalUser = quizPortalUserRepository.findByUserEmail(quizPortalUserLoginDto.getUserEmail());
        if(quizPortalUser.isEmpty()) {
            throw new EmailNotExists("Email Does not Exists");
        }
        String encodedPassword =  quizPortalUser.get().getUserPassword();
        if(!(passwordEncoder.matches(String.valueOf(quizPortalUserLoginDto.getUserPassword()), encodedPassword))) {
            throw new InvalidPassword("password is incorrect");
        }


        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(quizPortalUserLoginDto.getUserEmail(), quizPortalUserLoginDto.getUserPassword());
        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        QuizPortalUser user = (QuizPortalUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userLoginDetails.put("userName", user.getUserFirstName());
        userLoginDetails.put("userEmail",user.getUserEmail());
        userLoginDetails.put("userId",user.getUserId().toString());
        for(QuizPortalUserRoleMap userRole: user.getUserRoleMap()) {
            userLoginDetails.put("userRole", userRole.getRoles().getRoleName());
        }
        return userLoginDetails;
    }

    @Override
    public List<QuizPortalUser> getListOfAllUser() {
      return quizPortalUserRepository.findAll();
    }
}
