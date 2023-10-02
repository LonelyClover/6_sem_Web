package ru.msu.cs.TheaterWeb.DAO.impl;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import ru.msu.cs.TheaterWeb.DAO.TicketPriceDAO;
import ru.msu.cs.TheaterWeb.entities.*;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TicketPriceDAOImpl extends CommonDAOImpl<TicketPrice> implements TicketPriceDAO {
    public TicketPriceDAOImpl() {
        super(TicketPrice.class);
    }

    @Override
    public List<TicketPrice> getByFilter(Filter filter) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<TicketPrice> query = builder.createQuery(TicketPrice.class);
            Root<TicketPrice> root = query.from(TicketPrice.class);
            Join<TicketPrice, Performance> join = root.join("performance");


            List<Predicate> predicates = new ArrayList<>();
            if (filter.getPerformanceId() != null) {
                predicates.add(builder.equal(join.get("id"), filter.getPerformanceId()));
            }

            if (!predicates.isEmpty())
                query.where(predicates.toArray(new Predicate[0]));

            return session.createQuery(query).getResultList();
        }
    }
}