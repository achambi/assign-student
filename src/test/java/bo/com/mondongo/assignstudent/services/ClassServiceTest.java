package bo.com.mondongo.assignstudent.services;

import bo.com.mondongo.assignstudent.dto.ResponseDto;
import bo.com.mondongo.assignstudent.entities.Clazz;
import bo.com.mondongo.assignstudent.entities.Student;
import bo.com.mondongo.assignstudent.repositories.ClazzRepository;
import bo.com.mondongo.assignstudent.repositories.StudentRepository;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import javax.persistence.EntityNotFoundException;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ClassServiceTest {

    @Mock
    private ClazzRepository classRepository;

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private ClassService classService;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

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
    public void getStudents() {
        Clazz clazz = new Clazz(1, "class-001", "Chemistry", "Chemistry");
        Set<Student> expected = new HashSet(Arrays.asList(
            new Student(1, "Daenerys", "Targaryen"),
            new Student(2, "Robert", "Baratheon"),
            new Student(3, "Jhon", "Snow")
        ));
        clazz.setStudents(expected);

        when(classRepository.findById(eq(clazz.getId()))).thenReturn(Optional.of(clazz));

        Set<Student> result = classService.getStudents(clazz.getId());

        assertNotNull(result);
        assertEquals(expected, result);
        verify(classRepository, times(1)).findById(eq(clazz.getId()));

        verifyNoMoreInteractions(classRepository);
    }

    @Test
    public void create() {
        Clazz clazz = new Clazz(1, "class-001", "Chemistry", "Chemistry");
        when(classRepository.save(eq(clazz))).thenReturn(clazz);
        ResponseDto responseDto = classService.create(clazz);

        assertEquals(Boolean.FALSE, responseDto.isError());
        assertEquals("Class created, id: 1", responseDto.getMessage());

        verify(classRepository, times(1)).save(eq(clazz));
        verifyNoMoreInteractions(classRepository);
    }

    @Test
    public void assign() {
        Clazz clazz = new Clazz(1, "class-001", "Chemistry", "Chemistry");
        Student student = new Student(100, "Jose", "Cortez");

        when(classRepository.findById(eq(clazz.getId()))).thenReturn(Optional.of(clazz));
        when(studentRepository.findById(eq(student.getId()))).thenReturn(Optional.of(student));

        ResponseDto response = classService.assign(clazz.getId(), student.getId());

        assertEquals(Boolean.FALSE, response.isError());
        assertEquals("Student 100 assigned to class 1", response.getMessage());
        assertTrue(clazz.getStudents().contains(student));

        verify(classRepository, times(1)).findById(eq(clazz.getId()));
        verify(studentRepository, times(1)).findById(eq(student.getId()));
        verify(classRepository, times(1)).save(eq(clazz));
        verifyNoMoreInteractions(classRepository);
    }

    @Test
    public void assign_classNotFound() {
        expectedEx.expect(EntityNotFoundException.class);
        expectedEx.expectMessage("Class id 2 not found in the system");

        Student student = new Student(100, "Jose", "Cortez");

        classService.assign(2, student.getId());
    }

    @Test
    public void assign_studentNotFound() {
        expectedEx.expect(EntityNotFoundException.class);
        expectedEx.expectMessage("Student id 200 not found in the system");
        Clazz clazz = new Clazz(1, "class-001", "Chemistry", "Chemistry");

        when(classRepository.findById(eq(clazz.getId()))).thenReturn(Optional.of(clazz));
        classService.assign(clazz.getId(), 200);
    }

    @Test
    public void update() {
        Clazz clazz = new Clazz(1, "class-001", "Chemistry", "Chemistry");

        when(classRepository.findById(eq(clazz.getId()))).thenReturn(Optional.of(clazz));
        when(classRepository.save(eq(clazz))).thenReturn(clazz);
        ResponseDto responseDto = classService.update(clazz);

        assertEquals(Boolean.FALSE, responseDto.isError());
        assertEquals("Class updated, id: 1", responseDto.getMessage());

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

        ResponseDto responseDto = classService.delete(clazz.getId());

        assertEquals(Boolean.FALSE, responseDto.isError());
        assertEquals("Student deleted!", responseDto.getMessage());

        verify(classRepository, times(1)).save(eq(clazz));
        verify(classRepository, times(1)).findById(eq(clazz.getId()));
    }

    @Test(expected = EntityNotFoundException.class)
    public void delete_recordNotFound() {
        Clazz clazz = new Clazz(1, "class-001", "Chemistry", "Chemistry");

        when(classRepository.findById(eq(clazz.getId()))).thenReturn(Optional.empty());
        classService.delete(clazz.getId());
    }
}