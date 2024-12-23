package com.OnlineQuizzApplication.OnlineQuizzApplication.Service;




import com.OnlineQuizzApplication.OnlineQuizzApplication.Entity.StudentInfo;
import com.OnlineQuizzApplication.OnlineQuizzApplication.Repository.StudentInfoRepository;
import org.springframework.stereotype.Service;

import java.util.List;


public interface StudentInfoService {



    public StudentInfo createStudent(StudentInfo student);
    public List<StudentInfo> getAllStudents() ;

    public StudentInfo updateStudent(Long id, StudentInfo updatedStudent) ;

    public void deleteStudent(Long id) ;

    StudentInfo getStudentById(Long id);
}

