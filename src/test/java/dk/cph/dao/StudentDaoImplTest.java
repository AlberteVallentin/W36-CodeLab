package dk.cph.dao;

import dk.cph.config.HibernateConfig;
import dk.cph.model.Course;
import dk.cph.model.CourseName;
import dk.cph.model.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class StudentDaoImplTest {
    private static EntityManagerFactory emf;
    private static StudentDaoImpl studentDao;
    private static CourseDaoImpl courseDao;

    Student s1,s2,s3;
    Course c1,c2,c3;


    @BeforeAll
    static void setUp() {
        HibernateConfig.setTestMode(true);
        emf = HibernateConfig.getEntityManagerFactory();

        studentDao = new StudentDaoImpl(emf);
        courseDao = new CourseDaoImpl(emf);
    }

    @AfterAll
    static void tearDownAll() {
        HibernateConfig.setTestMode(false);
    }

    @BeforeEach
    void setup() {
        EntityManager em = emf.createEntityManager();

        c1 = new Course();
        c1.setCourseName(CourseName.MATH);
        c1.setDescription("Basic Math");
        c1.setStartDate(LocalDate.of(2024, 9, 1));
        c1.setEndDate(LocalDate.of(2024, 12, 31));

        c2 = new Course();
        c2.setCourseName(CourseName.SCIENCE);
        c2.setDescription("Basic Science");
        c2.setStartDate(LocalDate.of(2024, 9, 1));
        c2.setEndDate(LocalDate.of(2024, 12, 31));

        s1 = new Student();
        s1.setName("John Doe");
        s1.setEmail("john.doe@example.com");
        s1.getCreatedAt(LocalDateTime.now());
        s1.setUpdatedAt(LocalDateTime.now());
        s1.getCourses().add(c1);

        s2 = new Student();
        s2.setName("Jane Smith");
        s2.setEmail("jane.smith@example.com");
        s2.getCreatedAt(LocalDateTime.now());
        s2.setUpdatedAt(LocalDateTime.now());
        s2.getCourses().add(c2);

        s3 = new Student();
        s3.setName("Alice Johnson");
        s3.setEmail("alice.johnson@example.com");
        s3.getCreatedAt(LocalDateTime.now());
        s3.setUpdatedAt(LocalDateTime.now());

        em.getTransaction().begin();
        em.createQuery("DELETE FROM Student").executeUpdate();
        em.createQuery("DELETE FROM Course").executeUpdate();
        em.persist(c1);
        em.persist(c2);
        em.persist(s1);
        em.persist(s2);
        em.persist(s3);
        em.getTransaction().commit();
    }

    @AfterEach
    void tearDown() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.createQuery("DELETE FROM Student").executeUpdate();
        em.createQuery("DELETE FROM Course").executeUpdate();
        em.getTransaction().commit();
        em.close();
    }

    @Test
    @DisplayName("Test that we can find all students")
    void findAll() {
        assertEquals(3, studentDao.findAll().size(), "There should be 3 students in the database.");
    }

    @Test
    @DisplayName("Test that we can persist a student entity")
    void persistEntity() {
        Student newStudent = new Student();
        newStudent.setName("Bob Brown");
        newStudent.setEmail("bob.brown@example.com");
        newStudent.setCreatedAt(LocalDateTime.now());
        newStudent.setUpdatedAt(LocalDateTime.now());
        studentDao.persistEntity(newStudent);
        assertNotNull(newStudent.getId(), "Student should have an ID after being persisted.");
    }

    @Test
    @DisplayName("Test that we can remove a student entity")
    void removeEntity() {
        Student student = studentDao.findEntity(s1.getId());
        studentDao.removeEntity(student.getId());
        assertEquals(2, studentDao.findAll().size(), "There should be 2 students left after removal.");
    }

    @Test
    @DisplayName("Test that we can find a student by ID")
    void findEntity() {
        Student foundStudent = studentDao.findEntity(s1.getId());
        assertNotNull(foundStudent, "Found student should not be null.");
        assertEquals(s1.getName(), foundStudent.getName(), "Names should match.");
    }

    @Test
    @DisplayName("Test that we can update a student entity")
    void updateEntity() {
        Student student = studentDao.findEntity(s1.getId());
        student.setName("John Updated");
        studentDao.updateEntity(student, student.getId());
        Student updatedStudent = studentDao.findEntity(s1.getId());
        assertEquals("John Updated", updatedStudent.getName(), "Student's name should be updated.");
    }

    @Test
    @DisplayName("Test that we can get all students as a set")
    void getAllStudentsAsSet() {
        Set<Student> studentSet = studentDao.getAllStudentsAsSet();
        assertEquals(3, studentSet.size(), "There should be 3 students in the set.");
    }
}