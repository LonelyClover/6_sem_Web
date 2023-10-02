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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes=TheaterWebApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations="classpath:application.properties")
public class TicketPriceDAOTest {
    @Autowired
    private TicketPriceDAO ticketPriceDAO;

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
        Theater theater = new Theater("Name 1", "Address 1");
        theaterDAO.save(theater);

        Director director = new Director("Name 1", theater);
        directorDAO.save(director);

        Play play = new Play("Name 1", Duration.ofHours(3), theater, director);
        playDAO.save(play);

        Performance performance1 = new Performance(LocalDateTime.of(LocalDate.of(2022, 1, 1), LocalTime.of(18, 0)), play);
        Performance performance2 = new Performance(LocalDateTime.of(LocalDate.of(2022, 1, 1), LocalTime.of(18, 0)), play);
        Performance performance3 = new Performance(LocalDateTime.of(LocalDate.of(2022, 1, 1), LocalTime.of(18, 0)), play);
        performanceDAO.save(performance1);
        performanceDAO.save(performance2);
        performanceDAO.save(performance3);

        ticketPriceDAO.save(new TicketPrice(performance1, PlaceType.PARTERRE, 500L));
        ticketPriceDAO.save(new TicketPrice(performance1, PlaceType.LODGE, 600L));
        ticketPriceDAO.save(new TicketPrice(performance1, PlaceType.MEZZANINE, 700L));
        ticketPriceDAO.save(new TicketPrice(performance2, PlaceType.BALCONY, 800L));
        ticketPriceDAO.save(new TicketPrice(performance2, PlaceType.PARTERRE, 900L));
        ticketPriceDAO.save(new TicketPrice(performance3, PlaceType.AMPHITHEATER, 1000L));
    }

    @BeforeAll
    @AfterEach
    void cleanup() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("TRUNCATE role RESTART IDENTITY CASCADE;").executeUpdate();
            session.createSQLQuery("ALTER SEQUENCE role_id_seq RESTART WITH 1;").executeUpdate();
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
    void testFilterPerformanceId() {
        TicketPriceDAO.Filter filter = TicketPriceDAO.Filter.builder().performanceId(1L).build();
        List<TicketPrice> l = ticketPriceDAO.getByFilter(filter);
        assertEquals(3, l.size());
    }
}