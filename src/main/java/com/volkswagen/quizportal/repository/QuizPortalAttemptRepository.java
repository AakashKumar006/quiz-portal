package com.volkswagen.quizportal.repository;

import com.volkswagen.quizportal.model.QuizPortalAttempt;
import com.volkswagen.quizportal.model.QuizPortalUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuizPortalAttemptRepository extends JpaRepository<QuizPortalAttempt, Integer> {

    Optional<List<QuizPortalAttempt>> findByAttemptedBy(QuizPortalUser user);
}
