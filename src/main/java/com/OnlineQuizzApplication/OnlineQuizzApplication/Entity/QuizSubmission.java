package com.OnlineQuizzApplication.OnlineQuizzApplication.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class QuizSubmission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long quizSubmissionId;

//    @OneToOne
//    private StudentInfo user;

    private String domain;
    private int totalQuestions;
    private int correctAnswers;
    private boolean isPassed;

    private int aptitudeScore;
    private int logicalScore;
    private int codingScore;
    private boolean isCorrect;
    private double scorePercentage;

    @OneToOne
    @JsonBackReference(value = "studentInfo")
    private StudentInfo studentInfo;

    // Getters and Setters
}

