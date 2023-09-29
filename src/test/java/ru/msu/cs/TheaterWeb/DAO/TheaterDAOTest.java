package ru.msu.cs.TheaterWeb.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.msu.cs.TheaterWeb.config.TheaterWebApplication;
import ru.msu.cs.TheaterWeb.entities.Theater;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes=TheaterWebApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations="classpath:application.properties")
public class TheaterDAOTest {
    @Autowired()
    private TheaterDAO theaterDAO;

    @Autowired
    private SessionFactory sessionFactory;

    @BeforeEach
    void init() {
        List<Theater> theaterList = new ArrayList<>();

        Theater theater1 = new Theater("Name 1", "Address 1");
        theaterList.add(theater1);
        Theater theater2 = new Theater("Name 2", "Address 2");
        theaterList.add(theater2);
        Theater theater3 = new Theater("Name 12", "Address 12");
        theaterList.add(theater3);

        theaterDAO.saveAll(theaterList);
    }

    @BeforeAll
    @AfterEach
    void destroy() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            NativeQuery query1 = session.createNativeQuery("TRUNCATE \"Theater\" RESTART IDENTITY CASCADE;");
            query1.executeUpdate();
            NativeQuery query2 = session.createNativeQuery("ALTER SEQUENCE \"Theater_ID_seq\" RESTART WITH 1;");
            query2.executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Test
    void test() {
        List<Theater> theaterList = theaterDAO.getAll();
        assertEquals(3, theaterList.size());
    }
}