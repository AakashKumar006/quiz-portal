package com.volkswagen.quizportal.repository;

import com.volkswagen.quizportal.model.QuizPortalRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuizPortalRoleRepository extends JpaRepository<QuizPortalRole, Integer> {

    Optional<QuizPortalRole> findByRoleName(String roleName);

    Optional<QuizPortalRole> findByRoleNameAndRoleActive(String role_user, Integer Isactive);
}
