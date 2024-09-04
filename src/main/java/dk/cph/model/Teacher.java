package dk.cph.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "teachers")
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NamedQueries({
        @NamedQuery(name = "Teacher.findByName", query = "SELECT t FROM Teacher t WHERE t.name = :name"),
        @NamedQuery(name = "Teacher.deleteAll", query = "DELETE FROM Teacher"),
        @NamedQuery(name = "Teacher.findAll", query = "SELECT t FROM Teacher t"),
        @NamedQuery(name = "Teacher.findByZoom", query = "SELECT t FROM Teacher t WHERE t.zoom = :zoom")
})
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

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Course> courses = new HashSet<>();


}


