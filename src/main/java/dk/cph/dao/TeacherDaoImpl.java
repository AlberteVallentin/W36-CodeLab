package dk.cph.dao;

import dk.cph.model.Teacher;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TeacherDaoImpl implements GenericDAO<Teacher,Object> {

    private static TeacherDaoImpl instance;
    private static EntityManagerFactory emf;

    public static TeacherDaoImpl getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new TeacherDaoImpl();
        }
        return instance;
    }

    @Override
    public List<Teacher> findAll() {
        try (EntityManager em = emf.createEntityManager()) {
            // Create a query to select all teachers
            return em.createQuery("SELECT t FROM Teacher t", Teacher.class).getResultList();
        }
    }

    @Override
    public void persistEntity(Teacher entity) {
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
            Teacher teacher = em.find(Teacher.class, id);
            if (teacher != null) {
                // If the teacher is found, remove it
                em.remove(teacher);
            }

            em.getTransaction().commit();
        }
    }

    @Override
    public Teacher findEntity(Object id) {
        try (EntityManager em = emf.createEntityManager()) {
            // Use find method to retrieve the entity by its id
            return em.find(Teacher.class, id);
        }
    }

    @Override
    public Teacher updateEntity(Teacher entity, Object id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            // Find the current state of the entity in the database
            Teacher existingTeacher = em.find(Teacher.class, id);
            if (existingTeacher != null) {
                // If found, update fields of the existing teacher
                existingTeacher.setName(entity.getName());
                existingTeacher.setEmail(entity.getEmail());
                existingTeacher.setZoom(entity.getZoom());
                existingTeacher.setCourses(entity.getCourses());

                // Merge the changes
                em.merge(existingTeacher);
            }

            em.getTransaction().commit();
            return existingTeacher;
        }
    }

    // Optional: Method to delete all teachers using Named Query
    public void deleteAllTeachers() {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createNamedQuery("Teacher.deleteAll").executeUpdate();
            em.getTransaction().commit();
        }
    }
}
