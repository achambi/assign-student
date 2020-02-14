package bo.com.mondongo.assignstudent.repositories;

import bo.com.mondongo.assignstudent.entities.Clazz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.io.Serializable;
import java.util.List;

@Repository("ClazzRepository")
public interface ClazzRepository extends JpaRepository<Clazz, Serializable> {
    List<Clazz> findAll();
}
