package com.volkswagen.quizportal.repository;

import com.volkswagen.quizportal.model.QuizPortalAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizPortalAttemptRepository extends JpaRepository<QuizPortalAttempt, Integer> {
}
