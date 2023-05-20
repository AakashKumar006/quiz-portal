package com.volkswagen.quizportal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "tbl_quiz_portal_user_role_map")
public class QuizPortalUserRoleMap {

    @Id
    @Column(name = "user_role_map_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userRoleMapId;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_user_id")
    private QuizPortalUser users;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_role_id")
    private QuizPortalRole roles;

    public QuizPortalUserRoleMap() {
    }

    public QuizPortalUserRoleMap(QuizPortalUser users, QuizPortalRole roles) {
        this.users = users;
        this.roles = roles;
    }

    public Integer getUserRoleMapId() {
        return userRoleMapId;
    }

    public void setUserRoleMapId(Integer userRoleMapId) {
        this.userRoleMapId = userRoleMapId;
    }

    public QuizPortalUser getUsers() {
        return users;
    }

    public void setUsers(QuizPortalUser users) {
        this.users = users;
    }

    public QuizPortalRole getRoles() {
        return roles;
    }

    public void setRoles(QuizPortalRole roles) {
        this.roles = roles;
    }
}
