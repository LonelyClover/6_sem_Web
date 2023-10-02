package ru.msu.cs.TheaterWeb.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.msu.cs.TheaterWeb.TheaterWebApplication;
import ru.msu.cs.TheaterWeb.entities.Place;
import ru.msu.cs.TheaterWeb.entities.PlaceType;
import ru.msu.cs.TheaterWeb.entities.Theater;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes=TheaterWebApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations="classpath:application.properties")
public class PlaceDAOTest {
    @Autowired
    private PlaceDAO placeDAO;

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

        placeDAO.save(new Place("1", PlaceType.AMPHITHEATER, theater1));
        placeDAO.save(new Place("2", PlaceType.AMPHITHEATER, theater2));
        placeDAO.save(new Place("12", PlaceType.BALCONY, theater3));
    }

    @BeforeAll
    @AfterEach
    void cleanup() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("TRUNCATE place RESTART IDENTITY CASCADE;").executeUpdate();
            session.createSQLQuery("ALTER SEQUENCE place_id_seq RESTART WITH 1;").executeUpdate();
            session.createSQLQuery("TRUNCATE theater RESTART IDENTITY CASCADE;").executeUpdate();
            session.createSQLQuery("ALTER SEQUENCE theater_id_seq RESTART WITH 1;").executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Test
    void testFilterPlaceName() {
        PlaceDAO.Filter filter = PlaceDAO.Filter.builder().placeType(PlaceType.AMPHITHEATER).build();
        List<Place> l = placeDAO.getByFilter(filter);
        assertEquals(2, l.size());
    }

    @Test
    void testFilterTheaterName() {
        PlaceDAO.Filter filter = PlaceDAO.Filter.builder().theaterName("2").build();
        List<Place> l = placeDAO.getByFilter(filter);
        assertEquals(2, l.size());
    }

    @Test
    void testFilterTheaterId() {
        PlaceDAO.Filter filter = PlaceDAO.Filter.builder().theaterId(1L).build();
        List<Place> l = placeDAO.getByFilter(filter);
        assertEquals(1, l.size());
    }
}