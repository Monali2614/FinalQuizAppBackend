package com.OnlineQuizzApplication.OnlineQuizzApplication.Controller;



import com.OnlineQuizzApplication.OnlineQuizzApplication.Entity.Question;
import com.OnlineQuizzApplication.OnlineQuizzApplication.Entity.QuestionType;
import com.OnlineQuizzApplication.OnlineQuizzApplication.Service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
@CrossOrigin(origins = "http://localhost:4200")
public class QuestionController {
    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    private QuestionService questionService;

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping()
    public ResponseEntity<List<Question>> getQuestions() {
        logger.info("Fetching all questions");
        List<Question> questions = questionService.getAllQuestions();
        return ResponseEntity.ok(questions);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/{id}")
    public ResponseEntity<Question> getQuestionById(@PathVariable Long id) {
        logger.info("Fetching question with ID: {}", id);
        Question question = questionService.getQuestionById(id);
        return ResponseEntity.ok(question);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping()
    public ResponseEntity<Question> createQuestion(@RequestBody Question question) {
        logger.info("Creating new question: {}", question.getQuestionText());
        Question savedQuestion = questionService.saveQuestion(question);
        return ResponseEntity.ok(savedQuestion);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PutMapping("/{id}")
    public ResponseEntity<Question> updateQuestion(@PathVariable Long id, @RequestBody Question question) {
        logger.info("Updating question with ID: {}", id);
        Question updatedQuestion = questionService.updateQuestion(id, question);
        return ResponseEntity.ok(updatedQuestion);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteQuestion(@PathVariable Long id) {
        logger.info("Deleting question with ID: {}", id);
        questionService.deleteQuestion(id);
        return ResponseEntity.ok("Question deleted successfully.");
    }



    @GetMapping("/all-types")
    public ResponseEntity<?> getQuestionsByAllTypes() {
        try {
            logger.info("Fetching 15 shuffled questions for each type.");
            List<Question> questions = questionService.getQuestionsByAllTypes();

            if (questions.isEmpty()) {
                logger.warn("No questions found for the specified types.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No questions found for the specified types.");
            }

            logger.info("Successfully fetched questions for all types.");
            return ResponseEntity.ok(questions);
        } catch (Exception e) {
            logger.error("An error occurred while fetching questions for all types", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while processing your request. Please try again later.");
        }
    }


}