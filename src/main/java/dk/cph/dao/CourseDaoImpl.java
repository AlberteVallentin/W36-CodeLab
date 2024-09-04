package dk.cph.dao;

import dk.cph.model.Course;
import dk.cph.model.Student;
import dk.cph.model.Teacher;
import jakarta.persistence.EntityManagerFactory;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CourseDaoImpl implements GenericDAO<Course, Integer> {

    private static CourseDaoImpl instance;
    private static EntityManagerFactory emf;

    public static CourseDaoImpl getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new CourseDaoImpl();
        }
        return instance;
    }

    @Override
    public List<Course> findAll() {
        return List.of();

    }

    @Override
    public void persistEntity(Course entity) {
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
            Course course = emf.createEntityManager().find(Course.class, id);
            emf.createEntityManager().remove(course);
            emf.createEntityManager().getTransaction().commit();
        } catch (Exception e) {
            emf.createEntityManager().getTransaction().rollback();
        }

    }

    @Override
    public Course findEntity(Integer id) {
        try {
            return emf.createEntityManager().find(Course.class, id);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Course updateEntity(Course entity, Integer id) {
        try {
            emf.createEntityManager().getTransaction().begin();
            emf.createEntityManager().merge(entity);
            emf.createEntityManager().getTransaction().commit();
            return entity;
        } catch (Exception e) {
            emf.createEntityManager().getTransaction().rollback();
        }
        return entity;
    }

    public Course findCourseByName(String name, EntityManagerFactory emf) {
        try {
            return emf.createEntityManager().createNamedQuery("Course.findByName", Course.class).setParameter("description", name).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public Course findCourseByCourseName(String name, EntityManagerFactory emf) {
        try {
            return emf.createEntityManager().createNamedQuery("Course.findByCourseName", Course.class).setParameter("courseName", name).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public Course findCourseByStartDate(String startDate, EntityManagerFactory emf) {
        try {
            return emf.createEntityManager().createNamedQuery("Course.findByStartDate", Course.class).setParameter("startDate", startDate).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public Course findCourseByEndDate(String endDate, EntityManagerFactory emf) {
        try {
            return emf.createEntityManager().createNamedQuery("Course.findByEndDate", Course.class).setParameter("endDate", endDate).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public Set<Student> getStudents(int id) {
        try {
            return emf.createEntityManager().find(Course.class, id).getStudents();
        } catch (Exception e) {
            return Set.of();
        }
    }

    public Teacher getTeacher(int id) {
        try {
            return emf.createEntityManager().find(Course.class, id).getTeacher();
        } catch (Exception e) {
            return null;
        }
    }


}
