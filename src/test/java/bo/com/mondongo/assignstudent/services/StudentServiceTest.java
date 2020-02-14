package bo.com.mondongo.assignstudent.services;

import bo.com.mondongo.assignstudent.entities.Student;
import bo.com.mondongo.assignstudent.repositories.StudentRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    @After
    public void tearDown() {

        verifyNoMoreInteractions(studentRepository);
    }

    @Test
    public void listAll() {
        List<Student> students = new ArrayList<>();
        when(studentRepository.findAll()).thenReturn(students);
        List<Student> expected = new ArrayList<>();
        expected.add(new Student("Pepe", "Mamani"));
        expected.add(new Student("Jose", "Mendoza"));
        expected.add(new Student("Manuel", "Morales"));
        expected.add(new Student("Luis", "Loza"));

        when(studentRepository.findAll()).thenReturn(expected);

        List<Student> result = studentService.getAll();
        assertNotNull(result);
        assertEquals(expected, result);

        verify(studentRepository, times(1)).findAll();
    }

    @Test
    public void create() {
        Student student = new Student("Jose", "Cortez");
        student.setId(1);
        when(studentRepository.save(eq(student))).thenReturn(student);
        ResponseEntity responseEntity = studentService.create(student);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Map result = (Map) (responseEntity.getBody());
        assertEquals(student.getId(), result.get("id"));
        verify(studentRepository, times(2)).save(eq(student));
    }
}