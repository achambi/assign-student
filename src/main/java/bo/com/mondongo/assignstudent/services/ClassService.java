package bo.com.mondongo.assignstudent.services;

import bo.com.mondongo.assignstudent.entities.Clazz;
import bo.com.mondongo.assignstudent.entities.Student;
import bo.com.mondongo.assignstudent.repositories.ClazzRepository;
import bo.com.mondongo.assignstudent.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityNotFoundException;
import java.util.*;

@Service("ClassService")
public class ClassService {

    private final ClazzRepository clazzRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public ClassService(@Qualifier("ClazzRepository") ClazzRepository classRepository,
                        @Qualifier("StudentRepository") StudentRepository studentRepository) {
        this.clazzRepository = classRepository;
        this.studentRepository = studentRepository;
    }

    public List<Clazz> getAll() {
        return clazzRepository.findAll();
    }

    @Transactional
    public ResponseEntity create(Clazz clazz) {
        clazz = clazzRepository.save(clazz);
        Map<String, Object> response = new HashMap();
        response.put("id", clazz.getId());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity update(Clazz clazz) {
        Optional<Clazz> optionalClazz = clazzRepository.findById(clazz.getId());

        Clazz currentClazz = optionalClazz.orElseThrow(
            () -> new EntityNotFoundException("class id " + clazz.getId() + " not found in the system"));

        currentClazz.update(clazz);
        clazzRepository.save(currentClazz);
        Map<String, Object> response = new HashMap();
        response.put("id", clazz.getId());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity delete(Integer id) {
        Optional<Clazz> optionalClazz = clazzRepository.findById(id);

        Clazz currentClazz = optionalClazz.orElseThrow(
            () -> new EntityNotFoundException("class id " + id + " not found in the system"));
        currentClazz.delete();
        clazzRepository.save(currentClazz);
        Map<String, Object> response = new HashMap();
        response.put("id", id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity assign(int classId, int studentId) {

        Optional<Clazz> optionalClazz = clazzRepository.findById(classId);
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        Clazz currentClazz = optionalClazz.orElseThrow(
            () -> new EntityNotFoundException("class id " + classId + " not found in the system"));
        Student student = optionalStudent.orElseThrow(
            () -> new EntityNotFoundException("student id " + studentId + " not found in the system"));

        currentClazz.getStudents().add(student);
        clazzRepository.save(currentClazz);

        Map<String, Object> response = new HashMap();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public Set<Student> getStudents(Integer id) {
        Optional<Clazz> optionalClazz = clazzRepository.findById(id);

        Clazz currentClazz = optionalClazz.orElseThrow(
            () -> new EntityNotFoundException("class id " + id + " not found in the system"));
        return currentClazz.getStudents();
    }
}
