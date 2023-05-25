package com.volkswagen.quizportal.repository;

import com.volkswagen.quizportal.model.QuizPortalUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface QuizPortalUserRepository extends JpaRepository<QuizPortalUser, Integer> {

    Optional<QuizPortalUser> findByUserEmail(String userEmail);
}
