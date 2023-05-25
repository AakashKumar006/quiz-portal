package com.volkswagen.quizportal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "tbl_quiz_portal_attempt")
public class QuizPortalAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attempt_id")
    private Integer attemptId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "topic")
    private QuizPortalTopic topic;

    @OneToOne
    @JoinColumn(name = "attempted_by")
    private QuizPortalUser attemptedBy;

    @Column(name = "marks_obtained")
    private Integer marksObtained;

    @Column(name = "attempted_on")
    private LocalDate attemptedOn = LocalDate.now();

    public QuizPortalAttempt(QuizPortalTopic topic, QuizPortalUser attemptedBy, Integer marksObtained) {
        this.topic = topic;
        this.attemptedBy = attemptedBy;
        this.marksObtained = marksObtained;
    }
}
