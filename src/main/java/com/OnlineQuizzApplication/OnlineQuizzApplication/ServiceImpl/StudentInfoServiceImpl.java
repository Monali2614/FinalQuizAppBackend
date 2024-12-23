package com.OnlineQuizzApplication.OnlineQuizzApplication.ServiceImpl;


import com.OnlineQuizzApplication.OnlineQuizzApplication.Entity.StudentInfo;
import com.OnlineQuizzApplication.OnlineQuizzApplication.Repository.StudentInfoRepository;
import com.OnlineQuizzApplication.OnlineQuizzApplication.Service.StudentInfoService;
import com.OnlineQuizzApplication.OnlineQuizzApplication.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentInfoServiceImpl implements StudentInfoService {
    private final static Logger logger= LoggerFactory.getLogger(StudentInfoServiceImpl.class);


    @Autowired
    private final StudentInfoRepository repository;

    public StudentInfoServiceImpl(StudentInfoRepository repository) {
        this.repository = repository;
    }


    public StudentInfo createStudent(StudentInfo student) {
 logger.info("Saving student "+student.getName(),student.getMono());
 if(repository.existsByEmailId(student.getEmailId()) || repository.existsByMono(student.getMono())){
     logger.error("Email_Id {} and mobile number {} already exist ",student.getEmailId(),student.getMono());
     throw new RuntimeException("Email "+student.getEmailId()+" And "+student.getMono()+" Already exist .");
 }
        logger.info("Attempting to save new student: {}", student);
        return repository.save(student);
    }

    public StudentInfo getStudentById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id " + id));
    }

    public List<StudentInfo> getAllStudents() {
        return repository.findAll();
    }

    public StudentInfo updateStudent(Long id, StudentInfo updatedStudent) {
        StudentInfo existingStudent = getStudentById(id);
        updatedStudent.setId(existingStudent.getId());
        return repository.save(updatedStudent);
    }

    public void deleteStudent(Long id) {
        StudentInfo student = getStudentById(id);
        repository.delete(student);
    }
}
