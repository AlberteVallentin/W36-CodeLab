package dk.cph.dao;

import dk.cph.model.Course;
import dk.cph.model.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CourseDaoImpl implements GenericDAO<Course,Object>{

    private static CourseDaoImpl instance;
    private static EntityManagerFactory emf;

    public CourseDaoImpl(EntityManagerFactory emf) {
        this.emf=emf;
    }

    public static CourseDaoImpl getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new CourseDaoImpl();
        }
        return instance;
    }

    @Override
    public List<Course> findAll() {
        try (EntityManager em = emf.createEntityManager()) {
            // Create a query to select all students
            return em.createQuery("SELECT c FROM Course c", Course.class).getResultList();
        }
    }
    @Override
    public void persistEntity(Course entity) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
        }
    }

    @Override
    public void removeEntity(Object id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            // Find the entity by its id first
            Course course = em.find(Course.class, id);
            if (course != null) {
                // If the course is found, remove it
                em.remove(course);
            }

            em.getTransaction().commit();
        }
    }

    @Override
    public Course findEntity(Object id) {
        try (EntityManager em = emf.createEntityManager()) {
            // Use find method to retrieve the entity by its id
            return em.find(Course.class, id);
        }
    }

    @Override
    public Course updateEntity(Course entity, Object id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            // Find the current state of the entity in the database
            Course existingCourse = em.find(Course.class, id);
            if (existingCourse != null) {
                // If found, update fields of the existing course


                existingCourse.setDescription(entity.getDescription());
                existingCourse.setStartDate(entity.getStartDate());
                existingCourse.setEndDate(entity.getEndDate());
                existingCourse.setCourseName(entity.getCourseName());
                existingCourse.setTeacher(entity.getTeacher());
                existingCourse.setStudents(entity.getStudents());

                // No explicit `setUpdatedAt()` method, assume it's managed elsewhere or implicitly by actions

                // Merge the changes
                em.merge(existingCourse);
            }

            em.getTransaction().commit();
            return existingCourse;
        }
    }

}
