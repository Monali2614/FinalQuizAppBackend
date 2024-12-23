package com.OnlineQuizzApplication.OnlineQuizzApplication.Repository;

import com.OnlineQuizzApplication.OnlineQuizzApplication.Entity.UserScore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserScoreRepository extends JpaRepository<UserScore, Long> {
}
