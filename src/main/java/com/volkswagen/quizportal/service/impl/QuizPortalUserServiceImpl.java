package com.volkswagen.quizportal.service.impl;

import com.volkswagen.quizportal.exception.EmailAlreadyExists;
import com.volkswagen.quizportal.exception.EmailNotExists;
import com.volkswagen.quizportal.exception.InvalidPassword;
import com.volkswagen.quizportal.model.*;
import com.volkswagen.quizportal.payload.QuizPortalUserLoginDTO;
import com.volkswagen.quizportal.payload.QuizPortalUserRegistrationDTO;
import com.volkswagen.quizportal.repository.QuizPortalRoleRepository;
import com.volkswagen.quizportal.repository.QuizPortalUserRepository;
import com.volkswagen.quizportal.service.QuizPortalUserService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuizPortalUserServiceImpl implements QuizPortalUserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuizPortalUserServiceImpl.class);

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final QuizPortalUserRepository quizPortalUserRepository;
    private final QuizPortalRoleRepository quizPortalRoleRepository;
    private final QuizPortalRoleServiceImpl quizPortalRoleServiceImpl;
    private final AuthenticationProvider authenticationProvider;

    public QuizPortalUserServiceImpl(PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, QuizPortalUserRepository quizPortalUserRepository, QuizPortalRoleRepository quizPortalRoleRepository, QuizPortalRoleServiceImpl quizPortalRoleServiceImpl, AuthenticationProvider authenticationProvider) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.quizPortalUserRepository = quizPortalUserRepository;
        this.quizPortalRoleRepository = quizPortalRoleRepository;
        this.quizPortalRoleServiceImpl = quizPortalRoleServiceImpl;
        this.authenticationProvider = authenticationProvider;
    }

    @Override
    public Optional<QuizPortalUser> quizPortalUserRegistration(QuizPortalUserRegistrationDTO registrationDto) throws  EmailAlreadyExists {
        //DateTimeFormatter dataFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
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
        Optional<QuizPortalUser> returnUser  = quizPortalUserRepository.findByUserEmail(registrationDto.getUserEmail());
        return returnUser;
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
            System.out.println("invalid Password");
            throw new InvalidPassword("password is incorrect");
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        System.out.println("userName : "+ userName);
        Set<String> roles = authentication.getAuthorities().stream()
                .map(r -> r.getAuthority()).collect(Collectors.toSet());
        System.out.println("*** : *** : "+roles);






        // Must be called from request filtered by Spring Security, otherwise SecurityContextHolder is not updated



        QuizPortalUser user = quizPortalUserRepository.findByUserEmail(quizPortalUserLoginDto.getUserEmail()).get();
        userLoginDetails.put("userName", user.getUserFirstName());
        userLoginDetails.put("userEmail",user.getUserEmail());
        System.out.println("userId ::: "+user.getUserId());
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
