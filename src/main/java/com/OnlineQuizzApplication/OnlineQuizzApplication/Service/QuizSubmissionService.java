package com.OnlineQuizzApplication.OnlineQuizzApplication.Service;

import com.OnlineQuizzApplication.OnlineQuizzApplication.Entity.QuizSubmission;
import com.OnlineQuizzApplication.OnlineQuizzApplication.Entity.StudentInfo;

import java.util.Map;

public interface QuizSubmissionService {


    String evaluateUser(Long userId, String domain);

    QuizSubmission submitQuiz(StudentInfo user, String domain, Map<String, Map<Long, String>> userAnswers, Map<String, Integer> stepScores);

    QuizSubmission saveQuizResult(QuizSubmission quizResult);
}
