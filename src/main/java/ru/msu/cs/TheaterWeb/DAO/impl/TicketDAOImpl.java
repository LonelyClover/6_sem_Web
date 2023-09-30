package ru.msu.cs.TheaterWeb.DAO.impl;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import ru.msu.cs.TheaterWeb.DAO.TicketDAO;
import ru.msu.cs.TheaterWeb.entities.*;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
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
            Join<Ticket, Place> joinPlace = root.join("place");
            Join<Ticket, Performance> joinPerformance = root.join("performance");
            Join<Performance, Play> joinPlay = joinPerformance.join("play");
            Join<Play, Theater> joinTheater = joinPlay.join("theater");


            List<Predicate> predicates = new ArrayList<>();
            if (filter.getCustomerName() != null) {
                predicates.add(builder.like(root.get("customerName"), likeStr(filter.getCustomerName())));
            }
            if (filter.getCustomerPhoneNumber() != null) {
                predicates.add(builder.like(root.get("customerPhoneNumber"), likeStr(filter.getCustomerPhoneNumber())));
            }
            if (filter.getTheaterName() != null) {
                predicates.add(builder.like(joinTheater.get("name"), likeStr(filter.getTheaterName())));
            }
            if (filter.getPlayName() != null) {
                predicates.add(builder.like(joinPlay.get("name"), likeStr(filter.getPlayName())));
            }
            if (filter.getDate() != null) {
                predicates.add(builder.greaterThanOrEqualTo(joinPerformance.get("datetime"), filter.getDate().atStartOfDay()));
                predicates.add(builder.lessThan(joinPerformance.get("datetime"), filter.getDate().plusDays(1).atStartOfDay()));
            }
            if (filter.getPlaceType() != null) {
                predicates.add(builder.equal(joinPlace.get("placeType"), filter.getPlaceType()));
            }

            if (!predicates.isEmpty())
                query.where(predicates.toArray(new Predicate[0]));

            return session.createQuery(query).getResultList();
        }
    }
}