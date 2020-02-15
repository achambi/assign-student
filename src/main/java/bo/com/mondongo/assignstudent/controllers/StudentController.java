package bo.com.mondongo.assignstudent.controllers;

import bo.com.mondongo.assignstudent.entities.Student;
import bo.com.mondongo.assignstudent.services.StudentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(@Qualifier("StudentService") StudentService studentService) {
        this.studentService = studentService;
    }

    @ApiOperation(value = "List of students", response = List.class)
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public List<Student> getAllStudents() {
        return studentService.getAll();
    }

    @ApiOperation(value = "Create a student", response = ResponseEntity.class)
    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity create(@RequestBody Student student) {
        return studentService.create(student);
    }

    @ApiOperation(value = "Update a student", response = ResponseEntity.class)
    @PatchMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity update(@RequestParam("id") int id, @RequestBody Student student) {
        student.setId(id);
        return studentService.update(student);
    }

    @ApiOperation(value = "Delete a student", response = ResponseEntity.class)
    @DeleteMapping(produces = "application/json")
    public ResponseEntity delete(@RequestParam("id") int id) {
        return studentService.delete(id);
    }
}