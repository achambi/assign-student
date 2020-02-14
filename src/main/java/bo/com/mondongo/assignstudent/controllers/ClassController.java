package bo.com.mondongo.assignstudent.controllers;

import bo.com.mondongo.assignstudent.entities.Clazz;
import bo.com.mondongo.assignstudent.entities.Student;
import bo.com.mondongo.assignstudent.services.ClassService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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
    public List<Clazz> getAllStudents() {
        return classService.getAll();
    }

    @ApiOperation(value = "Create a class", response = ResponseEntity.class)
    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity create(@RequestBody Clazz clazz) {
        return classService.create(clazz);
    }

    @ApiOperation(value = "Update a class", response = ResponseEntity.class)
    @PatchMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity update(@RequestParam("id") int id, @RequestBody Clazz clazz) {
        clazz.setId(id);
        return classService.update(clazz);
    }

    @ApiOperation(value = "Delete a class", response = ResponseEntity.class)
    @DeleteMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity delete(@RequestParam("id") int id) {
        return classService.delete(id);
    }
}
