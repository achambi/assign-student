package bo.com.mondongo.assignstudent.services;

import bo.com.mondongo.assignstudent.entities.Clazz;
import bo.com.mondongo.assignstudent.entities.Student;
import bo.com.mondongo.assignstudent.repositories.ClazzRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service("ClassService")
public class ClassService {

    private final ClazzRepository clazzRepository;

    @Autowired
    public ClassService(@Qualifier("ClazzRepository") ClazzRepository classRepository) {
        this.clazzRepository = classRepository;
    }

    public List<Clazz> getAll() {
        return clazzRepository.findAll();
    }

    public ResponseEntity create(Clazz clazz) {
        clazz = clazzRepository.save(clazz);
        Map<String, Object> response = new HashMap();
        response.put("id", clazz.getId());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

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

    public ResponseEntity delete(Integer id) {
        Optional<Clazz> optionalClazz = clazzRepository.findById(id);

        Clazz currentClazz = optionalClazz.orElseThrow(
            () -> new EntityNotFoundException("student id " + id + " not found in the system"));
        currentClazz.delete();
        clazzRepository.save(currentClazz);
        Map<String, Object> response = new HashMap();
        response.put("id", id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
