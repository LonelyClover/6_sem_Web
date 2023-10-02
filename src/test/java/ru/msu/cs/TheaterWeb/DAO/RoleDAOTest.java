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
public class RoleDAOTest {
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
        Theater theater = new Theater("Name 1", "Address 1");
        theaterDAO.save(theater);

        Director director = new Director("Name 1", theater);
        directorDAO.save(director);

        Actor actor1 = new Actor("Name 1", theater);
        Actor actor2 = new Actor("Name 2", theater);
        Actor actor3 = new Actor("Name 3", theater);
        actorDAO.save(actor1);
        actorDAO.save(actor2);
        actorDAO.save(actor3);


        Play play1 = new Play("Name 1", Duration.ofHours(3), theater, director);
        Play play2 = new Play("Name 2", Duration.ofHours(2), theater, director);
        playDAO.save(play1);
        playDAO.save(play2);

        roleDAO.save(new Role(actor1, play1));
        roleDAO.save(new Role(actor2, play1));
        roleDAO.save(new Role(actor3, play1));
        roleDAO.save(new Role(actor1, play2));
        roleDAO.save(new Role(actor2, play2));
        roleDAO.save(new Role(actor3, play2));
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
    void testFilterRoleActorId() {
        RoleDAO.Filter filter = RoleDAO.Filter.builder().actorId(1L).build();
        List<Role> l = roleDAO.getByFilter(filter);
        assertEquals(2, l.size());
    }

    @Test
    void testFilterPlayId() {
        RoleDAO.Filter filter = RoleDAO.Filter.builder().playId(1L).build();
        List<Role> l = roleDAO.getByFilter(filter);
        assertEquals(3, l.size());
    }
}
