package bo.com.mondongo.assignstudent.controllers;

import bo.com.mondongo.assignstudent.dto.ResponseDto;
import bo.com.mondongo.assignstudent.entities.Clazz;
import bo.com.mondongo.assignstudent.entities.Student;
import bo.com.mondongo.assignstudent.services.ClassService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/classes")
public class ClassController {
    private final ClassService classService;

    @Autowired
    public ClassController(@Qualifier("ClassService") ClassService classService) {
        this.classService = classService;
    }

    @ApiOperation(value = "List of classes", response = List.class)
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public List<Clazz> getAll() {
        return classService.getAll();
    }

    @ApiOperation(value = "Create a class", response = ResponseEntity.class)
    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseDto create(@RequestBody Clazz clazz) {
        return classService.create(clazz);
    }

    @ApiOperation(value = "Assign a student to a class", response = ResponseEntity.class)
    @PatchMapping(value = "/{id}/assign/{studentId}", produces = "application/json", consumes = "application/json")
    public ResponseDto assign(@PathVariable("id") int id, @PathVariable("studentId") int studentId) {
        return classService.assign(id, studentId);
    }

    @ApiOperation(value = "Get all students assigned in a class", response = ResponseEntity.class)
    @GetMapping(value = "/{id}/students/", produces = "application/json")
    public Set<Student> assign(@PathVariable("id") int id) {
        return classService.getStudents(id);
    }

    @ApiOperation(value = "Update a class", response = ResponseEntity.class)
    @PatchMapping(produces = "application/json", consumes = "application/json")
    public ResponseDto update(@RequestParam("id") int id, @RequestBody Clazz clazz) {
        clazz.setId(id);
        return classService.update(clazz);
    }

    @ApiOperation(value = "Delete a class", response = ResponseEntity.class)
    @DeleteMapping(produces = "application/json")
    public ResponseDto delete(@RequestParam("id") int id) {
        return classService.delete(id);
    }
}
