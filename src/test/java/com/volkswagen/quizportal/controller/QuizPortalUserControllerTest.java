/*
package com.volkswagen.quizportal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.volkswagen.quizportal.payload.QuizPortalUserRegistrationDTO;
import com.volkswagen.quizportal.service.impl.QuizPortalUserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(QuizPortalUserController.class)
@AutoConfigureMockMvc(addFilters = false)
class QuizPortalUserControllerTest {

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private MockMvc mockMvc;

    @MockBean
    private QuizPortalUserServiceImpl quizPortalUserServiceImpl;



    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private ObjectMapper objectMapper;


    @Test
    void register() throws Exception{
 // given - precondition or setup

        QuizPortalUserRegistrationDTO registrationDto = new QuizPortalUserRegistrationDTO("aakash","asd","asd","asd","asd","asd","ad");




        given(quizPortalUserServiceImpl.quizPortalUserRegistration(any(QuizPortalUserRegistrationDTO.class))).willAnswer((invocation) -> invocation.getArgument(0));

        // when - action or behaviour that we are going to test

        ResultActions response = mockMvc.perform(post("http://localhost:8080/registration")

                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .contentType(objectMapper.writeValueAsString(registrationDto)));

        // then - verify the result or output assert statements

        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userFirstName",
                        is("aakash")) );



    }

    @Test
    void testRegister() {
    }
}
*/
