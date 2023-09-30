package ru.msu.cs.TheaterWeb.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.msu.cs.TheaterWeb.TheaterWebApplication;
import ru.msu.cs.TheaterWeb.entities.Director;
import ru.msu.cs.TheaterWeb.entities.Performance;
import ru.msu.cs.TheaterWeb.entities.Play;
import ru.msu.cs.TheaterWeb.entities.Theater;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes= TheaterWebApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations="classpath:application.properties")
public class PerformanceDAOTest {
    @Autowired
    private PerformanceDAO performanceDAO;

    @Autowired
    private PlayDAO playDAO;

    @Autowired
    private DirectorDAO directorDAO;

    @Autowired
    private TheaterDAO theaterDAO;

    @Autowired
    private SessionFactory sessionFactory;

    @BeforeEach
    void setup() {
        Theater theater = new Theater("Name", "Address");
        theaterDAO.save(theater);

        Director director = new Director("Name", theater);
        directorDAO.save(director);


        Play play = new Play("Name 1", Duration.ofHours(3), theater, director);
        playDAO.save(play);

        performanceDAO.save(new Performance(LocalDateTime.of(LocalDate.ofYearDay(2022, 1), LocalTime.of(18, 0, 0)), play));
        performanceDAO.save(new Performance(LocalDateTime.of(LocalDate.ofYearDay(2022, 1), LocalTime.of(20, 0, 0)), play));
        performanceDAO.save(new Performance(LocalDateTime.of(LocalDate.ofYearDay(2022, 2), LocalTime.of(18, 0, 0)), play));
    }

    @BeforeAll
    @AfterEach
    void cleanup() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("TRUNCATE performance RESTART IDENTITY CASCADE;").executeUpdate();
            session.createSQLQuery("ALTER SEQUENCE performance_id_seq RESTART WITH 1;").executeUpdate();
            session.createSQLQuery("TRUNCATE play RESTART IDENTITY CASCADE;").executeUpdate();
            session.createSQLQuery("ALTER SEQUENCE play_id_seq RESTART WITH 1;").executeUpdate();
            session.createSQLQuery("TRUNCATE director RESTART IDENTITY CASCADE;").executeUpdate();
            session.createSQLQuery("ALTER SEQUENCE director_id_seq RESTART WITH 1;").executeUpdate();
            session.createSQLQuery("TRUNCATE theater RESTART IDENTITY CASCADE;").executeUpdate();
            session.createSQLQuery("ALTER SEQUENCE theater_id_seq RESTART WITH 1;").executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Test
    void testFilterLocalDate() {
        PerformanceDAO.Filter filter = PerformanceDAO.Filter.builder().date(LocalDate.ofYearDay(2022, 1)).build();
        List<Performance> l = performanceDAO.getByFilter(filter);
        assertEquals(2, l.size());
    }
}