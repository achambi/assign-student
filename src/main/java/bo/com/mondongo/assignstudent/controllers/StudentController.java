package bo.com.mondongo.assignstudent.controllers;

import bo.com.mondongo.assignstudent.entities.Student;
import bo.com.mondongo.assignstudent.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;


@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(@Qualifier("StudentService") StudentService studentService) {
        this.studentService = studentService;
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public List<Student> getAllStudents() {
        return studentService.getAll();
    }
}