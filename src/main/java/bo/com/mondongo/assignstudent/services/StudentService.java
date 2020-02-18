package bo.com.mondongo.assignstudent.services;

import bo.com.mondongo.assignstudent.dto.ResponseDto;
import bo.com.mondongo.assignstudent.entities.Student;
import bo.com.mondongo.assignstudent.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityNotFoundException;
import java.util.*;

@Service("StudentService")
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(@Qualifier("StudentRepository") StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAll() {
        return studentRepository.findAll();
    }

    @Transactional
    public ResponseDto create(Student student) {
        student = studentRepository.save(student);
        return new ResponseDto(Boolean.TRUE, String.format("Student created, id: %d", student.getId()));
    }

    @Transactional
    public ResponseDto update(Student student) {

        Optional<Student> optionalStudent = studentRepository.findById(student.getId());

        Student currentStudent = optionalStudent.orElseThrow(
            () -> new EntityNotFoundException(String.format("student id %d not found in the system", student.getId())));

        currentStudent.update(student);
        currentStudent = studentRepository.save(currentStudent);
        return new ResponseDto(Boolean.FALSE, String.format("Student updated, id: %d", currentStudent.getId()));
    }

    @Transactional
    public ResponseDto delete(Integer id) {
        Optional<Student> optionalStudent = studentRepository.findById(id);

        Student currentStudent = optionalStudent.orElseThrow(
            () -> new EntityNotFoundException(String.format("student id %d not found in the system", id)));
        currentStudent.delete();
        studentRepository.save(currentStudent);
        return new ResponseDto(Boolean.FALSE, "Student deleted!");
    }
}


