package com.OnlineQuizzApplication.OnlineQuizzApplication.Service;

import com.OnlineQuizzApplication.OnlineQuizzApplication.Entity.UserScore;
import java.util.List;
import java.util.Optional;

public interface UserScoreService {
    UserScore saveUserScore(UserScore userScore);
    List<UserScore> getAllUserScores();
    UserScore calculateScore(UserScore userScore);
    Optional<UserScore> getUserScoreById(Long id);
    UserScore updateUserScore(Long id, UserScore userScore);
    boolean deleteUserScore(Long id);
}
