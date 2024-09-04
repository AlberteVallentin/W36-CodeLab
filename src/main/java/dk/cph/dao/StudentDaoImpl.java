package dk.cph.dao;

import dk.cph.model.Course;
import dk.cph.model.Student;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StudentDaoImpl implements GenericDAO<Student, Integer> {

    private static StudentDaoImpl instance;
    private static EntityManagerFactory emf;

    public static StudentDaoImpl getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new StudentDaoImpl();
        }
        return instance;
    }

    @Override
    public List<Student> findAll() {
        try {
            return emf.createEntityManager().createNamedQuery("Student.findAll", Student.class).getResultList();
        } catch (Exception e) {
            return List.of();
        }
    }

    @Override
    public void persistEntity(Student entity) {
        try {
            emf.createEntityManager().getTransaction().begin();
            emf.createEntityManager().persist(entity);
            emf.createEntityManager().getTransaction().commit();
        } catch (Exception e) {
            emf.createEntityManager().getTransaction().rollback();
        }
    }

    @Override
    public void removeEntity(Integer id) {
        try {
            emf.createEntityManager().getTransaction().begin();
            Student student = emf.createEntityManager().find(Student.class, id);
            emf.createEntityManager().remove(student);
            emf.createEntityManager().getTransaction().commit();
        } catch (Exception e) {
            emf.createEntityManager().getTransaction().rollback();
        }

    }

    @Override
    public Student findEntity(Integer id) {
        try {
            return emf.createEntityManager().find(Student.class, id);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Student updateEntity(Student entity, Integer id) {
        try {
            emf.createEntityManager().getTransaction().begin();
            Student student = emf.createEntityManager().find(Student.class, id);
            emf.createEntityManager().merge(entity);
            emf.createEntityManager().getTransaction().commit();
            return student;
        } catch (Exception e) {
            emf.createEntityManager().getTransaction().rollback();
            return null;
        }
    }

    public Student findStudentByName(String name) {
        try {
            return emf.createEntityManager().createNamedQuery("Student.findByName", Student.class).setParameter("name", name).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public Student findStudentByEmail(String email) {
        try {
            return emf.createEntityManager().createNamedQuery("Student.findByEmail", Student.class).setParameter("email", email).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public Set<Course> getCourses(int id) {
        try {
            return emf.createEntityManager().find(Student.class, id).getCourses();
        } catch (Exception e) {
            return null;
        }
    }

}
