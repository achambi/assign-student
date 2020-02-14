package bo.com.mondongo.assignstudent.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "student")
public class Student extends EntityBase implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "first_name", length = 25)
    private String firstName;

    @Column(name = "last_name", length = 25)
    private String lastName;

    public Student(int id, String firstName, String lastName) {
        this(firstName, lastName);
        this.id = id;
    }

    public Student(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Student() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null) {
            if (obj instanceof Student) {
                Student student = (Student) obj;
                return this.id == student.id &&
                    this.firstName.equals(student.firstName) &&
                    this.lastName.equals(student.lastName);
            }
        }
        return false;
    }

    public void update(Student student) {
        this.firstName = student.firstName;
        this.lastName = student.lastName;
        this.setEditedAt(LocalDateTime.now());
    }
}
