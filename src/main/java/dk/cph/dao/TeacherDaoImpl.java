package dk.cph.dao;

import dk.cph.model.Course;
import dk.cph.model.Teacher;
import jakarta.persistence.EntityManagerFactory;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TeacherDaoImpl implements GenericDAO<Teacher, Integer> {

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
        try {
            return emf.createEntityManager().createNamedQuery("Teacher.findAll", Teacher.class).getResultList();
        } catch (Exception e) {
            return List.of();
        }
    }

    @Override
    public void persistEntity(Teacher entity) {
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
            Teacher teacher = emf.createEntityManager().find(Teacher.class, id);
            emf.createEntityManager().remove(teacher);
            emf.createEntityManager().getTransaction().commit();
        } catch (Exception e) {
            emf.createEntityManager().getTransaction().rollback();
        }

    }

    @Override
    public Teacher findEntity(Integer id) {
        try {
            return emf.createEntityManager().find(Teacher.class, id);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Teacher updateEntity(Teacher entity, Integer id) {
        try {
            emf.createEntityManager().getTransaction().begin();
            emf.createEntityManager().merge(entity);
            emf.createEntityManager().getTransaction().commit();
            return entity;
        } catch (Exception e) {
            return null;
        }
    }

    public Teacher findByName(String name) {
        try {
            return emf.createEntityManager().createNamedQuery("Teacher.findByName", Teacher.class).setParameter("name", name).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public Teacher findByZoom(String zoom) {
        try {
            return emf.createEntityManager().createNamedQuery("Teacher.findByZoom", Teacher.class).setParameter("zoom", zoom).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public Set<Course> getCourses(int id) {
        try {
            return emf.createEntityManager().find(Teacher.class, id).getCourses();
        } catch (Exception e) {
            return Set.of();
        }
    }


}
