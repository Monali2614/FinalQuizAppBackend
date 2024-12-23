package com.OnlineQuizzApplication.OnlineQuizzApplication.Repository;

import com.OnlineQuizzApplication.OnlineQuizzApplication.Entity.QuizSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizSubmissionRepository extends JpaRepository<QuizSubmission,Long> {

    List<QuizSubmission> findByQuizSubmissionIdAndDomain(Long userId, String domain);
}
