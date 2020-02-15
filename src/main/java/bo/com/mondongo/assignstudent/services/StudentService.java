package bo.com.mondongo.assignstudent.services;

import bo.com.mondongo.assignstudent.entities.Student;
import bo.com.mondongo.assignstudent.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity create(Student student) {
        student = studentRepository.save(student);
        Map<String, Object> response = new HashMap();
        response.put("id", student.getId());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity update(Student student) {

        Optional<Student> optionalStudent = studentRepository.findById(student.getId());

        Student currentStudent = optionalStudent.orElseThrow(
            () -> new EntityNotFoundException("student id " + student.getId() + " not found in the system"));

        currentStudent.update(student);
        studentRepository.save(currentStudent);
        Map<String, Object> response = new HashMap();
        response.put("id", student.getId());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity delete(Integer id) {
        Optional<Student> optionalStudent = studentRepository.findById(id);

        Student currentStudent = optionalStudent.orElseThrow(
            () -> new EntityNotFoundException("student id " + id + " not found in the system"));
        currentStudent.delete();
        studentRepository.save(currentStudent);
        Map<String, Object> response = new HashMap();
        response.put("id", id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}


