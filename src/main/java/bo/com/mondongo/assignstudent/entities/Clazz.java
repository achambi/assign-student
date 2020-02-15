package bo.com.mondongo.assignstudent.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "class")
public class Clazz extends EntityBase implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "code", length = 25)
    private String code;

    @Column(name = "title", length = 25)
    private String title;

    @Column(name = "description", length = 25)
    private String description;

    @JsonIgnore
    @ManyToMany(cascade = {
        CascadeType.PERSIST,
        CascadeType.MERGE
    })
    @JoinTable(
        name = "class_student",
        joinColumns = {@JoinColumn(name = "class_id")},
        inverseJoinColumns = {@JoinColumn(name = "student_id")}
    )
    private Set<Student> students = new HashSet<>();

    public Clazz(String code, String title, String description) {
        this.code = code;
        this.title = title;
        this.description = description;
    }

    public Clazz(int id, String code, String title, String description) {
        this(code, title, description);
        this.id = id;
    }

    public Clazz() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null) {
            if (obj instanceof Clazz) {
                Clazz clazz = (Clazz) obj;
                return this.id == clazz.id &&
                    this.code.equals(clazz.code) &&
                    this.title.equals(clazz.title) &&
                    this.description.equals(clazz.description);
            }
        }
        return false;
    }

    public void update(Clazz clazz) {
        this.code = clazz.code;
        this.title = clazz.title;
        this.description = clazz.description;
        this.setEditedAt(LocalDateTime.now());
    }
}
