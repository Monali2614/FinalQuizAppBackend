package com.OnlineQuizzApplication.OnlineQuizzApplication.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String questionText;

    @ElementCollection
    private List<String> options;

    private String correctAnswer;

    private String domain; // e.g., Java, Python

    @Enumerated(EnumType.STRING)
    private QuestionType questionType; // LogicalReasoning, Aptitude, etc.
}
