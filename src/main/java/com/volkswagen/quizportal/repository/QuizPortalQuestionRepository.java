package com.volkswagen.quizportal.repository;

import com.volkswagen.quizportal.model.QuizPortalQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizPortalQuestionRepository extends JpaRepository<QuizPortalQuestion,Integer> {

}
