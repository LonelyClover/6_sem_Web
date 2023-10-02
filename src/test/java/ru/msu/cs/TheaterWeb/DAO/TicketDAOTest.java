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
public class TicketDAOTest {
    @Autowired
    private TicketDAO ticketDAO;

    @Autowired
    private PlaceDAO placeDAO;

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


        Play play1 = new Play("Name 1", Duration.ofHours(3), theater1, director1);
        Play play2 = new Play("Name 2", Duration.ofHours(2), theater2, director2);
        Play play3 = new Play("Name 12", Duration.ofHours(1), theater3, director3);
        playDAO.save(play1);
        playDAO.save(play2);
        playDAO.save(play3);

        Performance performance1 = new Performance(LocalDateTime.of(LocalDate.ofYearDay(2022, 1), LocalTime.of(18, 0, 0)), play1);
        Performance performance2 = new Performance(LocalDateTime.of(LocalDate.ofYearDay(2022, 1), LocalTime.of(20, 0, 0)), play2);
        Performance performance3 = new Performance(LocalDateTime.of(LocalDate.ofYearDay(2022, 2), LocalTime.of(18, 0, 0)), play3);
        performanceDAO.save(performance1);
        performanceDAO.save(performance2);
        performanceDAO.save(performance3);

        Place place1 = new Place("Number 1", PlaceType.PARTERRE, theater1);
        Place place2 = new Place("Number 2", PlaceType.LODGE, theater2);
        Place place3 = new Place("Number 12", PlaceType.PARTERRE, theater3);
        placeDAO.save(place1);
        placeDAO.save(place2);
        placeDAO.save(place3);

        ticketDAO.save(new Ticket(performance1, place1, "Customer 1", "+12345"));
        ticketDAO.save(new Ticket(performance2, place2, "Customer 2", "+67890"));
        ticketDAO.save(new Ticket(performance3, place3, "Customer 12", "+13579"));
    }

    @BeforeAll
    @AfterEach
    void cleanup() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("TRUNCATE ticket RESTART IDENTITY CASCADE;").executeUpdate();
            session.createSQLQuery("ALTER SEQUENCE ticket_id_seq RESTART WITH 1;").executeUpdate();
            session.createSQLQuery("TRUNCATE place RESTART IDENTITY CASCADE;").executeUpdate();
            session.createSQLQuery("ALTER SEQUENCE place_id_seq RESTART WITH 1;").executeUpdate();
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
    void testFilterCustomerName() {
        TicketDAO.Filter filter = TicketDAO.Filter.builder().customerName("1").build();
        List<Ticket> l = ticketDAO.getByFilter(filter);
        assertEquals(2, l.size());
    }
    @Test
    void testFilterCustomerPhoneNumber() {
        TicketDAO.Filter filter = TicketDAO.Filter.builder().customerPhoneNumber("+1").build();
        List<Ticket> l = ticketDAO.getByFilter(filter);
        assertEquals(2, l.size());
    }

    @Test
    void testFilterTheaterName() {
        TicketDAO.Filter filter = TicketDAO.Filter.builder().theaterName("1").build();
        List<Ticket> l = ticketDAO.getByFilter(filter);
        assertEquals(2, l.size());
    }

    @Test
    void testFilterPlayName() {
        TicketDAO.Filter filter = TicketDAO.Filter.builder().playName("1").build();
        List<Ticket> l = ticketDAO.getByFilter(filter);
        assertEquals(2, l.size());
    }

    @Test
    void testFilterDate() {
        TicketDAO.Filter filter = TicketDAO.Filter.builder().date(LocalDate.ofYearDay(2022, 1)).build();
        List<Ticket> l = ticketDAO.getByFilter(filter);
        assertEquals(2, l.size());
    }

    @Test
    void testFilterPlaceType() {
        TicketDAO.Filter filter = TicketDAO.Filter.builder().placeType(PlaceType.PARTERRE).build();
        List<Ticket> l = ticketDAO.getByFilter(filter);
        assertEquals(2, l.size());
    }

    @Test
    void testFilterPerformanceId() {
        TicketDAO.Filter filter = TicketDAO.Filter.builder().performanceId(1L).build();
        List<Ticket> l = ticketDAO.getByFilter(filter);
        assertEquals(1, l.size());
    }
}