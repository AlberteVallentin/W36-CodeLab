package dk.cph.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "teachers")
@NoArgsConstructor
@Getter
@Setter
@ToString
@NamedQuery(name = "Teacher.deleteAll", query = "DELETE FROM Teacher")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "zoom", unique = true)
    private String zoom;

    @OneToMany(mappedBy = "teacher", fetch=FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Course> courses = new ArrayList<>();
}
