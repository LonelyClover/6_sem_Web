package ru.msu.cs.TheaterWeb.DAO.impl;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import ru.msu.cs.TheaterWeb.DAO.PlaceDAO;
import ru.msu.cs.TheaterWeb.entities.Place;
import ru.msu.cs.TheaterWeb.entities.Theater;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PlaceDAOImpl extends CommonDAOImpl<Place> implements PlaceDAO {
    public PlaceDAOImpl() {
        super(Place.class);
    }
    @Override
    public List<Place> getByFilter(Filter filter) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Place> query = builder.createQuery(Place.class);
            Root<Place> root = query.from(Place.class);
            Join<Place, Theater> join = root.join("theater");

            List<Predicate> predicates = new ArrayList<>();
            if (filter.getTheaterName() != null) {
                predicates.add(builder.like(join.get("name"), likeStr(filter.getTheaterName())));
            }
            if (filter.getPlaceType() != null) {
                predicates.add(builder.equal(root.get("placeType"), filter.getPlaceType()));
            }
            if (filter.getTheaterId() != null) {
                predicates.add((builder.equal(join.get("id"), filter.getTheaterId())));
            }

            if (!predicates.isEmpty())
                query.where(predicates.toArray(new Predicate[0]));

            return session.createQuery(query).getResultList();
        }
    }
}