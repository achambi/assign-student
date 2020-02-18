package bo.com.mondongo.assignstudent.services;

import bo.com.mondongo.assignstudent.dto.ResponseDto;
import bo.com.mondongo.assignstudent.entities.Clazz;
import bo.com.mondongo.assignstudent.entities.Student;
import bo.com.mondongo.assignstudent.repositories.ClazzRepository;
import bo.com.mondongo.assignstudent.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    public ResponseDto create(Clazz clazz) {
        clazz = clazzRepository.save(clazz);
        return new ResponseDto(Boolean.FALSE, String.format("Class created, id: %d", clazz.getId()));
    }

    @Transactional
    public ResponseDto update(Clazz clazz) {
        Optional<Clazz> optionalClazz = clazzRepository.findById(clazz.getId());

        Clazz currentClazz = optionalClazz.orElseThrow(
            () -> new EntityNotFoundException(String.format("Class id %d not found in the system", clazz.getId())));

        currentClazz.update(clazz);
        clazzRepository.save(currentClazz);
        return new ResponseDto(Boolean.FALSE, String.format("Class updated, id: %d", clazz.getId()));
    }

    @Transactional
    public ResponseDto delete(Integer id) {
        Optional<Clazz> optionalClazz = clazzRepository.findById(id);

        Clazz currentClazz = optionalClazz.orElseThrow(
            () -> new EntityNotFoundException(String.format("Class id %d not found in the system", id)));
        currentClazz.delete();
        clazzRepository.save(currentClazz);

        return new ResponseDto(Boolean.FALSE, "Student deleted!");
    }

    @Transactional
    public ResponseDto assign(int classId, int studentId) {

        Optional<Clazz> optionalClazz = clazzRepository.findById(classId);
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        Clazz currentClazz = optionalClazz.orElseThrow(
            () -> new EntityNotFoundException("Class id " + classId + " not found in the system"));
        Student student = optionalStudent.orElseThrow(
            () -> new EntityNotFoundException("Student id " + studentId + " not found in the system"));

        currentClazz.getStudents().add(student);
        clazzRepository.save(currentClazz);

        return new ResponseDto(Boolean.FALSE, String.format("Student %d assigned to class %d", studentId, classId));
    }

    public Set<Student> getStudents(Integer id) {
        Optional<Clazz> optionalClazz = clazzRepository.findById(id);

        Clazz currentClazz = optionalClazz.orElseThrow(
            () -> new EntityNotFoundException("Class id " + id + " not found in the system"));
        return currentClazz.getStudents();
    }
}
