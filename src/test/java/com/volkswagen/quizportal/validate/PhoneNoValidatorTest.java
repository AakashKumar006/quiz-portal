package com.volkswagen.quizportal.validate;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class PhoneNoValidatorTest {

    @Mock
    private PhoneNoValidator underTest;

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    @BeforeEach
    void setUp() {
        underTest = new PhoneNoValidator();

    }

    @ParameterizedTest
    @CsvSource({
            "918750112894,true",
            "A28750112894,false", // contains string
            "618750112894,false", // does not start with 0 || 91
            "9187501128943,false" // exceeded the digit count

    })
    void itShouldValidatePhoneNumber(String phoneNumber, boolean expected) {
        // when
        boolean isValid = underTest.isValid(phoneNumber, constraintValidatorContext);
        // then
        assertThat(isValid).isEqualTo(expected);
    }



}
