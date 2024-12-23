package com.OnlineQuizzApplication.OnlineQuizzApplication.Repository;


import com.OnlineQuizzApplication.OnlineQuizzApplication.Entity.StudentInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface StudentInfoRepository extends JpaRepository<StudentInfo, Long> {


//    boolean existsByEmailIdAndMono(String emailId,String mono);
boolean existsByEmailId(String emailId);
boolean existsByMono(String Mono);
}
