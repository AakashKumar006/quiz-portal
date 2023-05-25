package com.volkswagen.quizportal.model;
import com.volkswagen.quizportal.payload.QuizPortalTopicRequestDTO;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "tbl_quiz_portal_topic")
public class QuizPortalTopic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Topic_id")
    private Integer topicId;

    @Column(name = "topic_name")
    private String topicName;

    @Column(name = "description")
    private String description;

    @Column(name = "marksPerQuestion")
    private Integer marksPerQuestion;

    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<QuizPortalQuestion> question;

    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<QuizPortalAttempt> attempts;

    @OneToOne
    @JoinColumn(name = "created_by")
    private QuizPortalUser createdBy;

    @Column(name = "topic_created_on")
    private LocalDate createdOn = LocalDate.now();

    /**
     * Initiating new quiz will contains zero questions,
     * And Publish flag will set on not-publish (0),
     * To publish the quiz, at least one question must
     * be associated.
     */
    @Column(name = "publish")
    private Integer publish = 0; // 0 not-publish, 1 published

    @Column(name = "published_On")
    private LocalDate publishedOn;

    public QuizPortalTopic(QuizPortalTopicRequestDTO topicRequestDTO) {
        this.topicName = topicRequestDTO.topicName();
        this.description = topicRequestDTO.description();
        this.marksPerQuestion = topicRequestDTO.marksPerQuestion();
    }
}
