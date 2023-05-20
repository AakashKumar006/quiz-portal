package com.volkswagen.quizportal.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "tbl_quiz_portal_role")
public class QuizPortalRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_role_id")
    private Integer roleId;

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "role_description")
    private String roleDescription;

    @Column(name = "role_active")
    private Integer roleActive;

   /* @JsonManagedReference(value = "role-map")*/
    @OneToMany(mappedBy = "roles", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<QuizPortalUserRoleMap> roleUserMap;

    public QuizPortalRole(String roleName, String roleDescription, Integer roleActive) {
        this.roleName = roleName;
        this.roleDescription = roleDescription;
        this.roleActive = roleActive;
    }
}
