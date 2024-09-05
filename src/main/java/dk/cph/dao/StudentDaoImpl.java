package dk.cph.dao;

import dk.cph.model.Course;
import dk.cph.model.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StudentDaoImpl implements GenericDAO <Student, Object> {

    private static StudentDaoImpl instance;
    private static EntityManagerFactory emf;

    public StudentDaoImpl( EntityManagerFactory emf) {
        this.emf=emf;
    }

    public static StudentDaoImpl getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new StudentDaoImpl();
        }
        return instance;
    }


    @Override
    public List<Student> findAll() {
        try (EntityManager em = emf.createEntityManager()) {
            // Create a query to select all students
            return em.createQuery("SELECT s FROM Student s", Student.class).getResultList();
        }
    }

    @Override
    public void persistEntity(Student entity) {
        try(EntityManager em = emf.createEntityManager()){

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
            Student student = em.find(Student.class, id);
            if (student != null) {
                // If the student is found, remove it
                em.remove(student);
            }

            em.getTransaction().commit();
        }
    }

    @Override
    public Student findEntity(Object id) {
        try (EntityManager em = emf.createEntityManager()) {
            // Use find method to retrieve the entity by its id
            return em.find(Student.class, id);
        }
    }

    @Override
    public Student updateEntity(Student entity, Object id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            // Find the current state of the entity in the database
            Student existingStudent = em.find(Student.class, id);
            if (existingStudent != null) {
                // If found, update fields of the existing student
                existingStudent.setName(entity.getName());
                existingStudent.setEmail(entity.getEmail());

                // Set the updated_at to the current time
                existingStudent.setUpdatedAt(LocalDateTime.now());

                // Merge the changes
                em.merge(existingStudent);
            }

            em.getTransaction().commit();
            return existingStudent;
        }
    }

    public Set<Student> getAllStudentsAsSet() {
        try (EntityManager em = emf.createEntityManager()) {
            // Retrieve all students as a list and convert it to a set
            List<Student> studentList = em.createQuery("SELECT s FROM Student s", Student.class).getResultList();
            return new HashSet<>(studentList);
        }
    }


}
