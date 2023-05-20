package com.volkswagen.quizportal.validate;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class PasswordValidatorTest {

    @Mock
    private PasswordValidator underTest;

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    @BeforeEach
    void setUp() {
        underTest = new PasswordValidator();
    }

    @ParameterizedTest
    @CsvSource({
            "Pass@123,true",
            "pass@123,false", // when does not contain at least one capital letter
            "Pass123456,false", // when does not contain at lease one special character
            "Password, false", // when doest not contain on numeric character
            "pass,false", // less then 6 digit
            "Password@123456789,false" // more then 15 digits

    })
    void itShouldValidatePassword(String password, boolean expected) {
        // when
        boolean isValid = underTest.isValid(password, constraintValidatorContext);
        // then
        assertThat(isValid).isEqualTo(expected);
    }


}