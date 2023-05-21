package com.volkswagen.quizportal.repository;

import com.volkswagen.quizportal.model.QuizPortalQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizPortalQuestionRepository extends JpaRepository<QuizPortalQuestion,Integer> {

    @Query(value = "SELECT * FROM vw_quiz.tbl_quiz_portal_question where topic_id = ?", nativeQuery = true)
    List<QuizPortalQuestion> findQuestionBasedOnTopicId(Integer topicId);

    @Query(value = "SELECT * FROM tbl_quiz_portal_question where topic_id = ?", nativeQuery = true)
    List<QuizPortalQuestion> findQuestionWithCorrectOptionListBasedOnTopicId(Long topicId);
}
