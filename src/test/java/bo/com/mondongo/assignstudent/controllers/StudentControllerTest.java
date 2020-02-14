package bo.com.mondongo.assignstudent.controllers;

import bo.com.mondongo.assignstudent.entities.Student;
import bo.com.mondongo.assignstudent.services.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.List;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class StudentControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private StudentService studentService;

    @Before
    public void setUp() {
        objectMapper = new ObjectMapper();

        mockMvc = MockMvcBuilders
            .standaloneSetup(new StudentController(studentService))
            .build();
    }

    @After
    public void tearDown() {
        verifyNoMoreInteractions(studentService);
    }

    @Test
    public void getAllStudents() throws Exception {
        List<Student> students = Arrays.asList(
            new Student(1, "Daenerys", "Targaryen"),
            new Student(2, "Robert", "Baratheon"),
            new Student(3, "Jhon", "Snow")
        );

        when(studentService.getAll()).thenReturn(students);

        mockMvc.perform(MockMvcRequestBuilders.get("/students/"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.content().contentType(APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(3)))
               .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
               .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName", Matchers.is("Daenerys")))
               .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastName", Matchers.is("Targaryen")))

               .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is(2)))
               .andExpect(MockMvcResultMatchers.jsonPath("$[1].firstName", Matchers.is("Robert")))
               .andExpect(MockMvcResultMatchers.jsonPath("$[1].lastName", Matchers.is("Baratheon")))

               .andExpect(MockMvcResultMatchers.jsonPath("$[2].id", Matchers.is(3)))
               .andExpect(MockMvcResultMatchers.jsonPath("$[2].firstName", Matchers.is("Jhon")))
               .andExpect(MockMvcResultMatchers.jsonPath("$[2].lastName", Matchers.is("Snow")));

        Mockito.verify(studentService).getAll();
    }

    @Test
    public void createAccount() throws Exception {
        Student student = new Student("Daenerys", "Targaryen");

        mockMvc.perform(MockMvcRequestBuilders.post("/students/")
                                              .content(objectMapper.writeValueAsBytes(student))
                                              .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk());
        Mockito.verify(studentService).create(eq(student));
    }

    @Test
    public void updateAccount() throws Exception {

        Student student = new Student(1, "Daenerys", "Targaryen");

        mockMvc.perform(MockMvcRequestBuilders.patch("/students/")
                                              .content(objectMapper.writeValueAsBytes(student))
                                              .param("id", student.getId().toString())
                                              .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk());
        Mockito.verify(studentService).update(eq(student));
    }

    @Test
    public void deleteStudent() throws Exception {

        int studentId = 1;

        mockMvc.perform(MockMvcRequestBuilders.delete("/students/")
                                              .param("id", String.valueOf(studentId))
                                              .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk());
        Mockito.verify(studentService).delete(eq(studentId));
    }
}
