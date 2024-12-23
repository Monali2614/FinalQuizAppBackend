package com.OnlineQuizzApplication.OnlineQuizzApplication.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
public class UserScore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    @Pattern(regexp =  "^[A-Za-z ]+$", message = "Name must contain only alphabetic characters")
    @Column(nullable = false, unique = true)
    private String name;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    @Pattern(regexp = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$", message = "Email must be in lowercase")
    @Column(nullable = false, unique = true)
    private String email;

    @Pattern(regexp = "\\d{10}", message = "Phone number should be 10 digits")
    private String contactNo;

    private int score;

    private int attemptQuestions;
    private int totalQuestions;

    private int correctAnswers;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String domain;


}
