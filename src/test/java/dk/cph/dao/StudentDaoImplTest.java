package dk.cph.dao;

import dk.cph.config.HibernateConfig;
import dk.cph.model.Course;
import dk.cph.model.CourseName;
import dk.cph.model.Student;
import dk.cph.model.Teacher;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class StudentDaoImplTest {
    private static EntityManagerFactory emf;
    private static StudentDaoImpl studentDao;

    Student s1, s2, s3;
    Course c1, c2, c3;
    Teacher t1, t2, t3;

    @BeforeAll
    static void setUpAll() {
        HibernateConfig.setTestMode(true);
        emf = HibernateConfig.getEntityManagerFactoryForTest();
        studentDao = StudentDaoImpl.getInstance(emf);
    }

    @AfterAll
    static void tearDownAll() {
        HibernateConfig.setTestMode(false);
        if (emf != null) {
            emf.close();
        }
    }

    @BeforeEach
    void setUp() {
        EntityManager em = emf.createEntityManager();
        s1 = Student.builder()
            .name("John Doe")
            .email("john@doe.com")
            .build();
        s2 = Student.builder()
            .name("Alice")
            .email("alice@alice.com")
            .build();
        s3 = Student.builder()
            .name("Charlie")
            .email("charlie@charlie.com")
            .build();
        c1 = Course.builder()
            .description("Math Course")
            .startDate(LocalDate.of(2023, 1, 1))
            .endDate(LocalDate.of(2023, 12, 31))
            .courseName(CourseName.MATH)
            .build();
        c2 = Course.builder()
            .description("English Course")
            .startDate(LocalDate.of(2023, 1, 1))
            .endDate(LocalDate.of(2023, 12, 31))
            .courseName(CourseName.ENGLISH)
            .build();
        c3 = Course.builder()
            .description("History Course")
            .startDate(LocalDate.of(2023, 1, 1))
            .endDate(LocalDate.of(2023, 12, 31))
            .courseName(CourseName.HISTORY)
            .build();
        t1 = Teacher.builder()
            .name("Mr. Smith")
            .email("mr@smith.com")
            .build();
        t2 = Teacher.builder()
            .name("Mrs. Johnson")
            .email("mrs@johnson.com")
            .build();
        t3 = Teacher.builder()
            .name("Mr. Brown")
            .email("mr@brown.com")
            .build();
        em.getTransaction().begin();
        em.createQuery("DELETE FROM Course").executeUpdate();
        em.createQuery("DELETE FROM Student").executeUpdate();
        em.createQuery("DELETE FROM Teacher").executeUpdate();


        em.persist(s1);
        em.persist(s2);
        em.persist(s3);
        em.persist(c1);
        em.persist(c2);
        em.persist(c3);
        em.persist(t1);
        em.persist(t2);
        em.persist(t3);
        em.getTransaction().commit();
    }

    @Test
    void getInstance() {
        assertNotNull(studentDao);
    }

    @Test
    void findAll() {
        assertEquals(3, studentDao.findAll().size());
    }

    @Test
    void persistEntity() {
        Student s4 = Student.builder()
            .name("Eve")
            .email("eve@eve.com")
            .build();
    }

    @Test
    void removeEntity() {

    }

    @Test
    void findEntity() {

    }


    @Test
    void updateEntity() {
    }

    @Test
    void findStudentByName() {

    }

    @Test
    void findStudentByEmail() {
    }

    @Test
    void getCourses() {
    }
}