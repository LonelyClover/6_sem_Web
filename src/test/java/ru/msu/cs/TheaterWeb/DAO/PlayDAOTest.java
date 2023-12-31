package ru.msu.cs.TheaterWeb.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.msu.cs.TheaterWeb.TheaterWebApplication;
import ru.msu.cs.TheaterWeb.entities.*;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes=TheaterWebApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations="classpath:application.properties")
public class PlayDAOTest {
    @Autowired
    private RoleDAO roleDAO;

    @Autowired
    private PlayDAO playDAO;

    @Autowired
    private ActorDAO actorDAO;

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

        Director director1 = new Director("Name 1", theater1);
        Director director2 = new Director("Name 2", theater2);
        Director director3 = new Director("Name 12", theater3);
        directorDAO.save(director1);
        directorDAO.save(director2);
        directorDAO.save(director3);

        Actor actor1 = new Actor("Name 1", theater1);
        Actor actor2 = new Actor("Name 2", theater2);
        Actor actor3 = new Actor("Name 3", theater3);
        actorDAO.save(actor1);
        actorDAO.save(actor2);
        actorDAO.save(actor3);

        Play play1 = new Play("Name 1", Duration.ofHours(3), theater1, director1);
        Play play2 = new Play("Name 2", Duration.ofHours(2), theater2, director2);
        Play play3 = new Play("Name 12", Duration.ofHours(1), theater3, director3);
        playDAO.save(play1);
        playDAO.save(play2);
        playDAO.save(play3);

        roleDAO.save(new Role(actor1, play1));
        roleDAO.save(new Role(actor2, play2));
        roleDAO.save(new Role(actor3, play3));
    }

    @BeforeAll
    @AfterEach
    void cleanup() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("TRUNCATE role RESTART IDENTITY CASCADE;").executeUpdate();
            session.createSQLQuery("ALTER SEQUENCE role_id_seq RESTART WITH 1;").executeUpdate();
            session.createSQLQuery("TRUNCATE play RESTART IDENTITY CASCADE;").executeUpdate();
            session.createSQLQuery("ALTER SEQUENCE play_id_seq RESTART WITH 1;").executeUpdate();
            session.createSQLQuery("TRUNCATE actor RESTART IDENTITY CASCADE;").executeUpdate();
            session.createSQLQuery("ALTER SEQUENCE actor_id_seq RESTART WITH 1;").executeUpdate();
            session.createSQLQuery("TRUNCATE director RESTART IDENTITY CASCADE;").executeUpdate();
            session.createSQLQuery("ALTER SEQUENCE director_id_seq RESTART WITH 1;").executeUpdate();
            session.createSQLQuery("TRUNCATE theater RESTART IDENTITY CASCADE;").executeUpdate();
            session.createSQLQuery("ALTER SEQUENCE theater_id_seq RESTART WITH 1;").executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Test
    void testFilterPlayName() {
        PlayDAO.Filter filter = PlayDAO.Filter.builder().playName("1").build();
        List<Play> l = playDAO.getByFilter(filter);
        assertEquals(2, l.size());
    }

    @Test
    void testFilterTheaterName() {
        PlayDAO.Filter filter = PlayDAO.Filter.builder().theaterName("2").build();
        List<Play> l = playDAO.getByFilter(filter);
        assertEquals(2, l.size());
    }

    @Test
    void testFilterTheaterId() {
        PlayDAO.Filter filter = PlayDAO.Filter.builder().theaterId(1L).build();
        List<Play> l = playDAO.getByFilter(filter);
        assertEquals(1, l.size());
    }

    @Test
    void testFilterDirectorId() {
        PlayDAO.Filter filter = PlayDAO.Filter.builder().directorId(1L).build();
        List<Play> l = playDAO.getByFilter(filter);
        assertEquals(1, l.size());
    }

    @Test
    void testFilterActorId() {
        PlayDAO.Filter filter = PlayDAO.Filter.builder().actorId(1L).build();
        List<Play> l = playDAO.getByFilter(filter);
        assertEquals(1, l.size());
    }
}
