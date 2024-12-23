package com.OnlineQuizzApplication.OnlineQuizzApplication.ServiceImpl;

import com.OnlineQuizzApplication.OnlineQuizzApplication.Entity.Status;
import com.OnlineQuizzApplication.OnlineQuizzApplication.Entity.UserScore;
import com.OnlineQuizzApplication.OnlineQuizzApplication.Repository.UserScoreRepository;
import com.OnlineQuizzApplication.OnlineQuizzApplication.Service.UserScoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserScoreServiceImpl implements UserScoreService {

    private static final Logger logger = LoggerFactory.getLogger(UserScoreServiceImpl.class);

    @Autowired
    private UserScoreRepository userScoreRepository;

    @Override
    public UserScore saveUserScore(UserScore userScore) {
        try {
            logger.info("Calculating score for user: {}", userScore.getName());
            calculateScore(userScore);
            logger.info("Saving user score: {}", userScore);
            return userScoreRepository.save(userScore);
        } catch (Exception e) {
            logger.error("Error while saving user score: {}", e.getMessage());
            throw new RuntimeException("Error saving user score");
        }
    }

    @Override
    public List<UserScore> getAllUserScores() {
        try {
            logger.info("Fetching all user scores");
            return userScoreRepository.findAll();
        } catch (Exception e) {
            logger.error("Error while fetching all user scores: {}", e.getMessage());
            throw new RuntimeException("Error fetching user scores");
        }
    }

//    @Override
//    public UserScore calculateScore(UserScore userScore) {
//        try {
//            int calculatedScore = userScore.getAttemptQuestions() * 2;
//            userScore.setScore(calculatedScore);
//            if (userScore.getScore() >= 80) {
//                userScore.setStatus(Status.PASS);
//            } else {
//                userScore.setStatus(Status.FAIL);
//            }
//            logger.info("Calculated score: {} and status: {}", userScore.getScore(), userScore.getStatus());
//            return userScore;
//        } catch (Exception e) {
//            logger.error("Error while calculating score for user: {}", e.getMessage());
//            throw new RuntimeException("Error calculating score");
//        }
//    }

    @Override
    public UserScore calculateScore(UserScore userScore) {
        try {
            if (userScore.getAttemptQuestions() <= 0) {
                throw new IllegalArgumentException("Attempted questions must be greater than zero.");
            }

            double calculatedScore = (userScore.getCorrectAnswers()/ (double)userScore.getTotalQuestions())*100;
            userScore.setScore((int)calculatedScore);

            // Determine the status based on the score
            if (userScore.getScore() >= 80) {
                userScore.setStatus(Status.PASS);
            } else {
                userScore.setStatus(Status.FAIL);
            }

            // Log the calculated score and status
            logger.info("Calculated score: {} and status: {}", userScore.getScore(), userScore.getStatus());
            return userScore;

        } catch (IllegalArgumentException e) {
            logger.error("Invalid input: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error while calculating score for user: {}", e.getMessage());
            throw new RuntimeException("Error calculating score");
        }
    }


    @Override
    public Optional<UserScore> getUserScoreById(Long id) {
        try {
            logger.info("Fetching user score by ID: {}", id);
            return userScoreRepository.findById(id);
        } catch (Exception e) {
            logger.error("Error while fetching user score by ID: {}", e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public UserScore updateUserScore(Long id, UserScore userScore) {
        try {
            Optional<UserScore> existingUserScore = userScoreRepository.findById(id);
            if (existingUserScore.isPresent()) {
                UserScore updatedUserScore = existingUserScore.get();
                updatedUserScore.setName(userScore.getName());
                updatedUserScore.setEmail(userScore.getEmail());
                updatedUserScore.setContactNo(userScore.getContactNo());
                updatedUserScore.setAttemptQuestions(userScore.getAttemptQuestions());
                updatedUserScore.setScore(userScore.getScore());
                updatedUserScore.setStatus(userScore.getStatus());
                logger.info("Updated user score: {}", updatedUserScore);
                return userScoreRepository.save(updatedUserScore);
            } else {
                logger.error("User score with ID: {} not found for update", id);
                throw new RuntimeException("User score not found for update");
            }
        } catch (Exception e) {
            logger.error("Error while updating user score with ID: {}: {}", id, e.getMessage());
            throw new RuntimeException("Error updating user score");
        }
    }

    @Override
    public boolean deleteUserScore(Long id) {
        try {
            Optional<UserScore> existingUserScore = userScoreRepository.findById(id);
            if (existingUserScore.isPresent()) {
                userScoreRepository.deleteById(id);
                logger.info("Deleted user score with ID: {}", id);
                return true;
            } else {
                logger.error("User score with ID: {} not found for deletion", id);
                return false;
            }
        } catch (Exception e) {
            logger.error("Error while deleting user score with ID: {}: {}", id, e.getMessage());
            return false;
        }
    }
}
