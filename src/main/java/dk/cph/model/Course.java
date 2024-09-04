package dk.cph.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "courses")
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NamedQueries({
        @NamedQuery(name = "Course.findByName", query = "SELECT c FROM Course c WHERE c.description = :description"),
        @NamedQuery(name = "Course.findByStartDate", query = "SELECT c FROM Course c WHERE c.startDate = :startDate"),
        @NamedQuery(name = "Course.findByEndDate", query = "SELECT c FROM Course c WHERE c.endDate = :endDate"),
        @NamedQuery(name = "Course.findByCourseName", query = "SELECT c FROM Course c WHERE c.courseName = :courseName"),
        @NamedQuery(name = "Course.deleteAll", query = "DELETE FROM Course"),
        @NamedQuery(name = "Course.findAll", query = "SELECT c FROM Course c")
})
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "course_name", nullable = false)
    private CourseName courseName;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "course_student",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private Set<Student> students;

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    private Teacher teacher;


}




