package bo.com.mondongo.assignstudent.repositories;

import bo.com.mondongo.assignstudent.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.io.Serializable;
import java.util.List;

@Repository("StudentRepository")
public interface StudentRepository extends JpaRepository<Student, Serializable> {
    List<Student> findAll();
}

