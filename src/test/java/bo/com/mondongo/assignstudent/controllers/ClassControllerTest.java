package bo.com.mondongo.assignstudent.controllers;

import bo.com.mondongo.assignstudent.entities.Clazz;
import bo.com.mondongo.assignstudent.entities.Student;
import bo.com.mondongo.assignstudent.services.ClassService;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class ClassControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private ClassService classService;

    @Before
    public void setUp() {
        objectMapper = new ObjectMapper();

        mockMvc = MockMvcBuilders
            .standaloneSetup(new ClassController(classService))
            .build();
    }

    @After
    public void tearDown() {
        verifyNoMoreInteractions(classService);
    }

    @Test
    public void getAll() throws Exception {
        List<Clazz> clazzes = Arrays.asList(
            new Clazz(1, "class-001", "Chemistry", "Chemistry"),
            new Clazz(2, "class-002", "Social Studies", "Social Studies"),
            new Clazz(3, "class-003", "Mathematics", "Mathematics"),
            new Clazz(4, "class-004", "Physical", "Physical")
        );

        when(classService.getAll()).thenReturn(clazzes);

        mockMvc.perform(MockMvcRequestBuilders.get("/classes/"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.content().contentType(APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(4)))
               .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
               .andExpect(MockMvcResultMatchers.jsonPath("$[0].code", Matchers.is("class-001")))
               .andExpect(MockMvcResultMatchers.jsonPath("$[0].title", Matchers.is("Chemistry")))
               .andExpect(MockMvcResultMatchers.jsonPath("$[0].description", Matchers.is("Chemistry")))

               .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is(2)))
               .andExpect(MockMvcResultMatchers.jsonPath("$[1].code", Matchers.is("class-002")))
               .andExpect(MockMvcResultMatchers.jsonPath("$[1].title", Matchers.is("Social Studies")))
               .andExpect(MockMvcResultMatchers.jsonPath("$[1].description", Matchers.is("Social Studies")))

               .andExpect(MockMvcResultMatchers.jsonPath("$[2].id", Matchers.is(3)))
               .andExpect(MockMvcResultMatchers.jsonPath("$[2].code", Matchers.is("class-003")))
               .andExpect(MockMvcResultMatchers.jsonPath("$[2].title", Matchers.is("Mathematics")))
               .andExpect(MockMvcResultMatchers.jsonPath("$[2].description", Matchers.is("Mathematics")))

               .andExpect(MockMvcResultMatchers.jsonPath("$[3].id", Matchers.is(4)))
               .andExpect(MockMvcResultMatchers.jsonPath("$[3].code", Matchers.is("class-004")))
               .andExpect(MockMvcResultMatchers.jsonPath("$[3].title", Matchers.is("Physical")))
               .andExpect(MockMvcResultMatchers.jsonPath("$[3].description", Matchers.is("Physical")));

        Mockito.verify(classService).getAll();
    }

    @Test
    public void create() throws Exception {
        Clazz clazz = new Clazz(1, "class-001", "Chemistry", "Chemistry");

        mockMvc.perform(MockMvcRequestBuilders.post("/classes/")
                                              .content(objectMapper.writeValueAsBytes(clazz))
                                              .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk());
        Mockito.verify(classService).create(eq(clazz));
    }

    @Test
    public void assignStudent() throws Exception {
        Student student = new Student(1, "Angel", "Chambi");
        Clazz clazz = new Clazz(1, "class-001", "Math", "Math");

        mockMvc.perform(MockMvcRequestBuilders.patch("/classes/{id}/assign/{studentId}",
                                                     String.valueOf(clazz.getId()), String.valueOf(student.getId())
        ).content(objectMapper.writeValueAsBytes(student)).contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk());
        Mockito.verify(classService).assign(eq(clazz.getId()), eq(student.getId()));
    }

    @Test
    public void getClasses() throws Exception {
        Student student = new Student(1, "Angel", "Chambi");
        Clazz clazz = new Clazz(1, "class-001", "Math", "Math");
        Set<Student> students = new HashSet(Arrays.asList(
            new Student(1, "Daenerys", "Targaryen"),
            new Student(2, "Robert", "Baratheon"),
            new Student(3, "Jhon", "Snow")
        ));

        clazz.setStudents(students);

        when(classService.getStudents(clazz.getId())).thenReturn(clazz.getStudents());

        mockMvc.perform(MockMvcRequestBuilders.get("/classes/{id}/students/", String.valueOf(clazz.getId()))
                                              .content(objectMapper.writeValueAsBytes(student))
                                              .contentType(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.content().contentType(APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(3)));

        Mockito.verify(classService).getStudents(eq(clazz.getId()));
    }

    @Test
    public void updateAccount() throws Exception {

        Clazz clazz = new Clazz(1, "class-001", "Math", "Math");

        mockMvc.perform(MockMvcRequestBuilders.patch("/classes/")
                                              .content(objectMapper.writeValueAsBytes(clazz))
                                              .param("id", clazz.getId().toString())
                                              .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk());
        Mockito.verify(classService).update(eq(clazz));
    }

    @Test
    public void deleteStudent() throws Exception {

        int classId = 1;

        mockMvc.perform(MockMvcRequestBuilders.delete("/classes/")
                                              .param("id", String.valueOf(classId))
                                              .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk());
        Mockito.verify(classService).delete(eq(classId));
    }
}
