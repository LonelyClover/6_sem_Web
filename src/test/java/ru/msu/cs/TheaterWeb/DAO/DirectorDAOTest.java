package ru.msu.cs.TheaterWeb.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.msu.cs.TheaterWeb.TheaterWebApplication;
import ru.msu.cs.TheaterWeb.entities.Director;
import ru.msu.cs.TheaterWeb.entities.Theater;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes=TheaterWebApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations="classpath:application.properties")
public class DirectorDAOTest {
    @Autowired
    private DirectorDAO directorDAO;

    @Autowired
    private TheaterDAO theaterDAO;

    @Autowired
    private SessionFactory sessionFactory;

    @BeforeEach
    void setup() {
        Theater theater1 = new Theater("Name 1", "Address 1");
        Theater theater2 = new Theater("Name 2", "Address 2");
        Theater theater3 = new Theater("Name 12", "Address 12");
        theaterDAO.save(theater1);
        theaterDAO.save(theater2);
        theaterDAO.save(theater3);

        directorDAO.save(new Director("Name 1", theater1));
        directorDAO.save(new Director("Name 2", theater2));
        directorDAO.save(new Director("Name 12", theater3));
    }

    @BeforeAll
    @AfterEach
    void cleanup() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("TRUNCATE director RESTART IDENTITY CASCADE;").executeUpdate();
            session.createSQLQuery("ALTER SEQUENCE director_id_seq RESTART WITH 1;").executeUpdate();
            session.createSQLQuery("TRUNCATE theater RESTART IDENTITY CASCADE;").executeUpdate();
            session.createSQLQuery("ALTER SEQUENCE theater_id_seq RESTART WITH 1;").executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Test
    void testFilterDirectorName() {
        DirectorDAO.Filter filter = DirectorDAO.Filter.builder().directorName("1").build();
        List<Director> l = directorDAO.getByFilter(filter);
        assertEquals(2, l.size());
    }

    @Test
    void testFilterTheaterName() {
        DirectorDAO.Filter filter = DirectorDAO.Filter.builder().theaterName("2").build();
        List<Director> l = directorDAO.getByFilter(filter);
        assertEquals(2, l.size());
    }

    @Test
    void testFilterTheaterId() {
        DirectorDAO.Filter filter = DirectorDAO.Filter.builder().theaterId(1L).build();
        List<Director> l = directorDAO.getByFilter(filter);
        assertEquals(1, l.size());
    }
}
