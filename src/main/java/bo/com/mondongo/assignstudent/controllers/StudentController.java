package bo.com.mondongo.assignstudent.controllers;

import bo.com.mondongo.assignstudent.dto.ResponseDto;
import bo.com.mondongo.assignstudent.entities.Student;
import bo.com.mondongo.assignstudent.services.StudentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.persistence.EntityNotFoundException;
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
    public ResponseDto create(@RequestBody Student student) {
        return studentService.create(student);
    }

    @ApiOperation(value = "Update a student", response = ResponseEntity.class)
    @PatchMapping(produces = "application/json", consumes = "application/json")
    public ResponseDto update(@RequestParam("id") int id, @RequestBody Student student) {
        student.setId(id);
        try {
            return studentService.update(student);
        } catch (EntityNotFoundException ex) {
            return new ResponseDto(true, String.format("Student %d not found!", id));
        }
    }

    @ApiOperation(value = "Delete a student", response = ResponseEntity.class)
    @DeleteMapping(produces = "application/json")
    public ResponseDto delete(@RequestParam("id") int id) {
        try {
            return studentService.delete(id);
        } catch (EntityNotFoundException ex) {
            return new ResponseDto(true, String.format("Student %d not found!", id));
        }
    }
}