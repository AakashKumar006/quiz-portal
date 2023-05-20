package com.volkswagen.quizportal.model;
import com.volkswagen.quizportal.payload.QuizPortalTopicRequestDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
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

    @Column(name = "number_of_question")
    private Integer numberOfQuestion;

    @Column(name = "marksPerQuestion")
    private Integer marksPerQuestion;

    @Column(name = "max_marks")
    private Integer maxMarks;

    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<QuizPortalQuestion> question;

    @OneToOne
    @JoinColumn(name = "created_by")
    private QuizPortalUser createdBy;

    @Column(name = "topic_created_on")
    private LocalDate createdOn = LocalDate.now();

    /**
     * Initiating new quiz will contains zero questions,
     * And Publish flag will set on not-publish (0),
     * To publish the quiz, number of questions added should
     * equals to number of questions assigned during initiation.
     */
    @Column(name = "publish")
    private Integer publish = 0; // 0 not-publish, 1 published

    public QuizPortalTopic(QuizPortalTopicRequestDTO topicRequestDTO) {
        this.topicName = topicRequestDTO.topicName();
        this.description = topicRequestDTO.description();
        this.numberOfQuestion = topicRequestDTO.numberOfQuestion();
        this.marksPerQuestion = topicRequestDTO.marksPerQuestion();
    }
}
