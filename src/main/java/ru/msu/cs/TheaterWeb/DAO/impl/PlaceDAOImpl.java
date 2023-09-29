package ru.msu.cs.TheaterWeb.DAO.impl;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import ru.msu.cs.TheaterWeb.DAO.PlaceDAO;
import ru.msu.cs.TheaterWeb.entities.Place;

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

            List<Predicate> predicates = new ArrayList<>();
            if (filter.getTheaterName() != null) {
                predicates.add(builder.like(root.get("theater.name"), likeStr(filter.getTheaterName())));
            }
            if (filter.getPlaceType() != null) {
                predicates.add(builder.equal(root.get("placeType"), filter.getPlaceType()));
            }

            if (!predicates.isEmpty())
                query.where(predicates.toArray(new Predicate[0]));

            return session.createQuery(query).getResultList();
        }
    }
}