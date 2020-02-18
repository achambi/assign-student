package bo.com.mondongo.assignstudent.services;

import bo.com.mondongo.assignstudent.dto.ResponseDto;
import bo.com.mondongo.assignstudent.entities.Student;
import bo.com.mondongo.assignstudent.repositories.StudentRepository;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    @Test
    public void listAll() {
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

        verifyNoMoreInteractions(studentRepository);
    }

    @Test
    public void create() {
        Student student = new Student("Jose", "Cortez");
        student.setId(1);
        when(studentRepository.save(eq(student))).thenReturn(student);
        ResponseDto response = studentService.create(student);

        assertEquals(Boolean.TRUE, response.isError());
        assertEquals("Student created, id: 1", response.getMessage());
        verify(studentRepository, times(1)).save(eq(student));

        verifyNoMoreInteractions(studentRepository);
    }

    @Test
    public void update() {
        Student student = new Student(1, "Jose", "Cortez");

        when(studentRepository.findById(eq(student.getId()))).thenReturn(java.util.Optional.of(student));
        when(studentRepository.save(eq(student))).thenReturn(student);
        ResponseDto response = studentService.update(student);

        assertEquals(Boolean.FALSE, response.isError());
        assertEquals("Student updated, id: 1", response.getMessage());

        verify(studentRepository, times(1)).save(eq(student));
        verify(studentRepository, times(1)).findById(eq(student.getId()));
    }

    @Test(expected = EntityNotFoundException.class)
    public void update_recordNotFound() {
        Student student = new Student(1, "Jose", "Cortez");

        when(studentRepository.findById(eq(student.getId()))).thenReturn(Optional.empty());
        studentService.update(student);
    }

    @Test
    public void delete() {
        Student student = new Student(1, "Jose", "Cortez");

        when(studentRepository.findById(eq(student.getId()))).thenReturn(java.util.Optional.of(student));

        ResponseDto response = studentService.delete(student.getId());

        assertFalse(student.getActive());
        assertFalse(response.isError());
        assertEquals("Student deleted!", response.getMessage());

        verify(studentRepository, times(1)).save(eq(student));
        verify(studentRepository, times(1)).findById(eq(student.getId()));
    }
}