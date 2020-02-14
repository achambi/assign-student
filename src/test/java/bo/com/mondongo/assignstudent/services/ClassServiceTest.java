package bo.com.mondongo.assignstudent.services;

import bo.com.mondongo.assignstudent.entities.Clazz;
import bo.com.mondongo.assignstudent.repositories.ClazzRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ClassServiceTest {

    @Mock
    private ClazzRepository classRepository;

    @InjectMocks
    private ClassService classService;

    @Test
    public void listAll() {
        List<Clazz> expected = new ArrayList<>();

        expected.add(new Clazz("class-001", "Chemistry", "Chemistry"));
        expected.add(new Clazz("class-002", "Social Studies", "Social Studies"));
        expected.add(new Clazz("class-003", "Mathematics", "Mathematics"));
        expected.add(new Clazz("class-004", "Physical", "Physical"));

        when(classRepository.findAll()).thenReturn(expected);

        List<Clazz> result = classService.getAll();
        assertNotNull(result);
        assertEquals(expected, result);

        verify(classRepository, times(1)).findAll();

        verifyNoMoreInteractions(classRepository);
    }

    @Test
    public void create() {
        Clazz clazz = new Clazz(1, "class-001", "Chemistry", "Chemistry");
        when(classRepository.save(eq(clazz))).thenReturn(clazz);
        ResponseEntity responseEntity = classService.create(clazz);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Map result = (Map) (responseEntity.getBody());
        assertNotNull(result);
        assertEquals(clazz.getId(), result.get("id"));
        verify(classRepository, times(1)).save(eq(clazz));

        verifyNoMoreInteractions(classRepository);
    }

    @Test
    public void update() {
        Clazz clazz = new Clazz(1, "class-001", "Chemistry", "Chemistry");

        when(classRepository.findById(eq(clazz.getId()))).thenReturn(Optional.of(clazz));
        when(classRepository.save(eq(clazz))).thenReturn(clazz);
        ResponseEntity responseEntity = classService.update(clazz);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Map result = (Map) (responseEntity.getBody());
        assertNotNull(result);
        assertEquals(clazz.getId(), result.get("id"));

        verify(classRepository, times(1)).save(eq(clazz));
        verify(classRepository, times(1)).findById(eq(clazz.getId()));
    }

    @Test(expected = EntityNotFoundException.class)
    public void update_recordNotFound() {
        Clazz clazz = new Clazz(1, "class-001", "Chemistry", "Chemistry");

        when(classRepository.findById(eq(clazz.getId()))).thenReturn(Optional.empty());
        classService.update(clazz);
    }

    @Test
    public void delete() {
        Clazz clazz = new Clazz(1, "class-001", "Chemistry", "Chemistry");

        when(classRepository.findById(eq(clazz.getId()))).thenReturn(Optional.of(clazz));

        ResponseEntity responseEntity = classService.delete(clazz.getId());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Map result = (Map) (responseEntity.getBody());
        assertNotNull(result);
        assertEquals(clazz.getId(), result.get("id"));

        verify(classRepository, times(1)).save(eq(clazz));
        verify(classRepository, times(1)).findById(eq(clazz.getId()));
    }
}