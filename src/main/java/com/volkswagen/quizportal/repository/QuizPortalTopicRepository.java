package com.volkswagen.quizportal.repository;

import com.volkswagen.quizportal.model.QuizPortalTopic;
import com.volkswagen.quizportal.model.QuizPortalUser;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface QuizPortalTopicRepository extends JpaRepository<QuizPortalTopic, Integer> {

    Optional<QuizPortalTopic> findByTopicId(Integer topicId);

    List<QuizPortalTopic> findByCreatedBy(QuizPortalUser user);

    List<QuizPortalTopic> findByPublish(Integer publishFlag);




}
