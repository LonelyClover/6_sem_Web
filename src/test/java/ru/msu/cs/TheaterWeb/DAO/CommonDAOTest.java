package ru.msu.cs.TheaterWeb.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.msu.cs.TheaterWeb.TheaterWebApplication;
import ru.msu.cs.TheaterWeb.entities.Theater;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes=TheaterWebApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations="classpath:application.properties")
public class CommonDAOTest {
    @Autowired
    private TheaterDAO theaterDAO;

    @Autowired
    private SessionFactory sessionFactory;

    @BeforeEach
    void setup() {
        theaterDAO.save(new Theater("Name 1", "Address 1"));
        theaterDAO.save(new Theater("Name 2", "Address 2"));
        theaterDAO.save(new Theater("Name 12", "Address 12"));
    }

    @BeforeAll
    @AfterEach
    void cleanup() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("TRUNCATE theater RESTART IDENTITY CASCADE;").executeUpdate();
            session.createSQLQuery("ALTER SEQUENCE theater_id_seq RESTART WITH 1;").executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Test
    void testGetById() {
        Theater t = theaterDAO.getById(1L);
        assertEquals("Name 1", t.getName());
    }
    @Test
    void testGetByIdNull() {
        Theater t = theaterDAO.getById(6L);
        assertNull(t);
    }
    @Test
    void testGetAll() {
        List<Theater> l = theaterDAO.getAll();
        assertEquals(3, l.size());
    }

    @Test
    void testSave() {
        Theater t = new Theater("Name 3", "Address 3");
        theaterDAO.save(t);

        List<Theater> l = theaterDAO.getAll();
        assertEquals(4, l.size());
    }

    @Test
    void testSaveAll() {
        List<Theater> l1 = new ArrayList<>();
        Theater t1 = new Theater("Name 3", "Address 3");
        l1.add(t1);
        Theater t2 = new Theater("Name 4", "Address 4");
        l1.add(t2);
        theaterDAO.saveAll(l1);

        List<Theater> l = theaterDAO.getAll();
        assertEquals(5, l.size());
    }

    @Test
    void testUpdate() {
        Theater t1 = theaterDAO.getById(1L);
        t1.setInfo("Very good");
        theaterDAO.update(t1);

        Theater t2 = theaterDAO.getById(1L);
        assertEquals("Very good", t2.getInfo());
    }

    @Test
    void testDelete() {
        Theater t = theaterDAO.getById(1L);
        theaterDAO.delete(t);

        List<Theater> l = theaterDAO.getAll();
        assertEquals(2, l.size());
    }
}