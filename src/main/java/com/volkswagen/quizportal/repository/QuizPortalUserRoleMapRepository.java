package com.volkswagen.quizportal.repository;

import com.volkswagen.quizportal.model.QuizPortalUserRoleMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizPortalUserRoleMapRepository extends JpaRepository<QuizPortalUserRoleMap, Integer> {
}
