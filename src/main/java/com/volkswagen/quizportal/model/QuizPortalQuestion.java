package com.volkswagen.quizportal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "tbl_quiz_portal_question")
public class QuizPortalQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Integer questionId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "topic_id")
    private QuizPortalTopic topic;

/*    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<QuizPortalAnswer> answer;*/

    @Column(name = "option_A")
    private String optionA;

    @Column(name = "option_B")
    private String optionB;

    @Column(name = "option_C")
    private String optionC;

    @Column(name = "option_D")
    private String optionD;

    @Column(name = "correct_option")
    private String correctOption;

    @Column(name = "question")
    private String question;


}
