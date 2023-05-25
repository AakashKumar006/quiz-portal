package com.volkswagen.quizportal.repository;

import com.volkswagen.quizportal.model.QuizPortalQuestion;
import com.volkswagen.quizportal.model.QuizPortalTopic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuizPortalQuestionRepository extends JpaRepository<QuizPortalQuestion,Integer> {

    @Query(value = "SELECT * FROM tbl_quiz_portal_question where topic_id = ?", nativeQuery = true)
    Optional<List<QuizPortalQuestion>> findListOfQuestionBasedOnTopicId(Integer topicId);

    Integer countByTopic(Optional<QuizPortalTopic> topicId);
}
