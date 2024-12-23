package com.OnlineQuizzApplication.OnlineQuizzApplication.Entity;



import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is mandatory")
    @Pattern(regexp = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$", message = "Email must be in lowercase")
    private String emailId;

    @Pattern(regexp = "\\d{10}", message = "Mobile number must be 10 digits")
    private String mono;

    @Min(value = 1, message = "Graduate years must be at least 1")
    private int graduateYears;

    @NotBlank(message = "Department name is mandatory")
    private String departmentName;

    @Min(value = 1900, message = "Invalid passout year")
    @Max(value = 2100, message = "Invalid passout year")
    private int passoutYears;

    @NotBlank(message = "College name is mandatory")
    private String collegeName;

//    @ElementCollection
//    @CollectionTable(name = "interest_domains", joinColumns = @JoinColumn(name = "student_id"))
//    @Column(name = "interest_domain")
@NotBlank(message = "interest_domains is mandatory")
    private String interestDomain;

    @OneToOne(mappedBy = "studentInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "studentInfo")
    private QuizSubmission quizSubmission;

}

