package com.OnlineQuizzApplication.OnlineQuizzApplication.ServiceImpl;

import com.OnlineQuizzApplication.OnlineQuizzApplication.Entity.Question;
import com.OnlineQuizzApplication.OnlineQuizzApplication.Entity.QuizSubmission;
import com.OnlineQuizzApplication.OnlineQuizzApplication.Entity.StudentInfo;
import com.OnlineQuizzApplication.OnlineQuizzApplication.Repository.QuestionRepository;
import com.OnlineQuizzApplication.OnlineQuizzApplication.Repository.QuizSubmissionRepository;
import com.OnlineQuizzApplication.OnlineQuizzApplication.Service.QuizSubmissionService;
import com.OnlineQuizzApplication.OnlineQuizzApplication.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class QuizSubmissionServiceImpl implements QuizSubmissionService {

    private static final Logger logger = LoggerFactory.getLogger(QuizSubmissionServiceImpl.class);

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuizSubmissionRepository quizSubmissionRepository;



//    public void saveUserResponse(QuizSubmission request) {
//        Question question = questionRepository.findById(request)
//                .orElseThrow(() -> new ResourceNotFoundException("Question not found"));
//
//        boolean isCorrect = question.getCorrectAnswer().equalsIgnoreCase(request.getUserAnswer());
//
//        UserResponse response = new UserResponse();
//        response.setUserId(request.getUserId());
//        response.setDomain(request.getDomain());
//        response.setStep(request.getStep());
//        response.setQuestionText(question.getQuestionText());
//        response.setUserAnswer(request.getUserAnswer());
//        response.setCorrect(isCorrect);
//
//        logger.info("User response saved for user: " + request.getUserId());
//        userResponseRepository.save(response);
//    }

    public QuizSubmission submitQuiz(
            StudentInfo user,
            String domain,
            Map<String, Map<Long, String>> userAnswers,
            Map<String, Integer> stepScores) {

        logger.info("Submitting quiz for user: {} in domain: {}", user.getName(), domain);
        int totalQuestions = 0;
        int correctAnswers = 0;

        for (String step : userAnswers.keySet()) {
            Map<Long, String> answers = userAnswers.get(step);
            List<Question> questions = questionRepository.findByDomain(domain);

            totalQuestions += questions.size();

            for (Question question : questions) {
                String correctAnswer = question.getCorrectAnswer();
                String userAnswer = answers.get(question.getId());
                if (correctAnswer.equalsIgnoreCase(userAnswer)) {
                    correctAnswers++;
                }
            }
        }

        boolean isPassed = ((double) correctAnswers / totalQuestions) >= 0.6;

        QuizSubmission submission = new QuizSubmission();
        submission.setStudentInfo(user);
        submission.setDomain(domain);
        submission.setTotalQuestions(totalQuestions);
        submission.setCorrectAnswers(correctAnswers);
        submission.setPassed(isPassed);

        submission.setAptitudeScore(stepScores.getOrDefault("aptitude", 0));
        submission.setLogicalScore(stepScores.getOrDefault("logical", 0));
        submission.setCodingScore(stepScores.getOrDefault("coding", 0));

        return quizSubmissionRepository.save(submission);
    }

    public String evaluateUser(Long userId, String domain) {
        List<QuizSubmission> responses = quizSubmissionRepository.findByQuizSubmissionIdAndDomain(userId, domain);

        long totalQuestions = responses.size();
        long correctAnswers = responses.stream().filter(QuizSubmission::isCorrect).count();

        double percentage = ((double) correctAnswers / totalQuestions) * 100;

        logger.info("User " + userId + " scored " + percentage + "% in domain " + domain);
        return percentage >= 60 ? "PASS" : "FAIL";
    }

    public QuizSubmission saveQuizResult(QuizSubmission quizResult) {
        // Calculate score percentage
        double scorePercentage = ((double) quizResult.getCorrectAnswers() / quizResult.getTotalQuestions()) * 100;
        quizResult.setScorePercentage(scorePercentage);

        // Determine pass/fail
        quizResult.setPassed(scorePercentage >= 70);

        // Save result to database
        return quizSubmissionRepository.save(quizResult);
    }
}
