package com.volkswagen.quizportal.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "tbl_quiz_portal_user")
public class QuizPortalUser  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_user_id")
    private Integer userId;

    @Column(name = "user_first_name")
    private String userFirstName;

    @Column(name = "user_middle_name")
    private String userMiddleName;

    @Column(name = "user_Last_name")
    private String userLastName;

    @Column(name = "user_date_of_birth")
    private LocalDate userDateOfBirth;

    @Column(name = "user_age")
    private Integer userAge;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "user_phone_no")
    private String userPhoneNo;

    @Column(name = "user_password")
    private String userPassword;

    @Column(name = "user_created_on")
    private LocalDate userCreatedOn;

    @Column(name = "user_isActive")
    private Integer isActive;

    @OneToMany(mappedBy = "users",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<QuizPortalUserRoleMap> userRoleMap;
}
