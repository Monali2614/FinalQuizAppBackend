package com.OnlineQuizzApplication.OnlineQuizzApplication.Controller;

import com.OnlineQuizzApplication.OnlineQuizzApplication.Entity.UserScore;
import com.OnlineQuizzApplication.OnlineQuizzApplication.Service.UserScoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/userscore")
@CrossOrigin(origins = "http://localhost:4200")
public class UserScoreController {

    private static final Logger logger = LoggerFactory.getLogger(UserScoreController.class);

    @Autowired
    private UserScoreService userScoreService;

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/save")
    public ResponseEntity<UserScore> saveUserScore(@RequestBody UserScore userScore) {
        try {
            logger.info("Received request to save user score: {}", userScore);
            UserScore savedUserScore = userScoreService.saveUserScore(userScore);
            return new ResponseEntity<>(savedUserScore, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error while saving user score: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/all")
    public ResponseEntity<List<UserScore>> getAllUserScores() {
        try {
            logger.info("Received request to fetch all user scores");
            List<UserScore> userScores = userScoreService.getAllUserScores();
            if (userScores.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(userScores, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while fetching user scores: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/{id}")
    public ResponseEntity<UserScore> getUserScoreById(@PathVariable Long id) {
        try {
            logger.info("Received request to fetch user score by ID: {}", id);
            Optional<UserScore> userScore = userScoreService.getUserScoreById(id);
            if (userScore.isPresent()) {
                return new ResponseEntity<>(userScore.get(), HttpStatus.OK);
            } else {
                logger.warn("User score with ID: {} not found", id);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error while fetching user score by ID: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PutMapping("/update/{id}")
    public ResponseEntity<UserScore> updateUserScore(@PathVariable Long id, @RequestBody UserScore userScore) {
        try {
            logger.info("Received request to update user score with ID: {}", id);
            UserScore updatedUserScore = userScoreService.updateUserScore(id, userScore);
            return new ResponseEntity<>(updatedUserScore, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while updating user score with ID: {}: {}", id, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUserScore(@PathVariable Long id) {
        try {
            logger.info("Received request to delete user score with ID: {}", id);
            boolean isDeleted = userScoreService.deleteUserScore(id);
            if (isDeleted) {
                return new ResponseEntity<>("User score deleted successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("User score not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error while deleting user score with ID: {}: {}", id, e.getMessage());
            return new ResponseEntity<>("Error deleting user score", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
