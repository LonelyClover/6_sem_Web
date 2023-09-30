package ru.msu.cs.TheaterWeb.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.msu.cs.TheaterWeb.TheaterWebApplication;
import ru.msu.cs.TheaterWeb.entities.Theater;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes=TheaterWebApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations="classpath:application.properties")
public class TheaterDAOTest {
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
    void testFilterTheaterName() {
        TheaterDAO.Filter filter = TheaterDAO.Filter.builder().theaterName("1").build();
        List<Theater> l = theaterDAO.getByFilter(filter);
        assertEquals(2, l.size());
    }

    @Test
    void testFilterTheaterAddress() {
        TheaterDAO.Filter filter = TheaterDAO.Filter.builder().theaterAddress("2").build();
        List<Theater> l = theaterDAO.getByFilter(filter);
        assertEquals(2, l.size());
    }
}