package ru.msu.cs.TheaterWeb.DAO.impl;

import org.hibernate.Session;
import ru.msu.cs.TheaterWeb.DAO.TicketDAO;
import ru.msu.cs.TheaterWeb.entities.Ticket;

import jakarta.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class TicketDAOImpl extends CommonDAOImpl<Ticket> implements TicketDAO {
    public TicketDAOImpl() {
        super(Ticket.class);
    }
    @Override
    public List<Ticket> getByFilter(Filter filter) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Ticket> query = builder.createQuery(Ticket.class);
            Root<Ticket> root = query.from(Ticket.class);

            List<Predicate> predicates = new ArrayList<>();
            if (filter.getCustomerName() != null) {
                predicates.add(builder.like(root.get("customerName"), likeStr(filter.getCustomerName())));
            }
            if (filter.getCustomerPhoneNumber() != null) {
                predicates.add(builder.like(root.get("customerPhoneNumber"), likeStr(filter.getCustomerPhoneNumber())));
            }
            if (filter.getTheaterName() != null) {
                predicates.add(builder.like(root.get("theater.name"), likeStr(filter.getTheaterName())));
            }
            if (filter.getPlayName() != null) {
                predicates.add(builder.like(root.get("performance.play.name"), likeStr(filter.getPlayName())));
            }
            if (filter.getDatetime() != null) {
                predicates.add(builder.equal(root.get("performance.datetime"), filter.getDatetime()));
            }
            if (filter.getPlaceType() != null) {
                predicates.add(builder.equal(root.get("place.placeType"), filter.getPlaceType()));
            }

            if (!predicates.isEmpty())
                query.where(predicates.toArray(new Predicate[0]));

            return session.createQuery(query).getResultList();
        }
    }
}