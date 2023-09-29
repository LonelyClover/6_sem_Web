package ru.msu.cs.TheaterWeb.DAO.impl;

import org.hibernate.Session;
import ru.msu.cs.TheaterWeb.DAO.PlayDAO;
import ru.msu.cs.TheaterWeb.entities.Play;

import jakarta.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class PlayDAOImpl extends CommonDAOImpl<Play> implements PlayDAO {
    public PlayDAOImpl() {
        super(Play.class);
    }
    @Override
    public List<Play> getByFilter(Filter filter) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Play> query = builder.createQuery(Play.class);
            Root<Play> root = query.from(Play.class);

            List<Predicate> predicates = new ArrayList<>();
            if (filter.getPlayName() != null) {
                predicates.add(builder.like(root.get("name"), likeStr(filter.getPlayName())));
            }
            if (filter.getTheaterName() != null) {
                predicates.add(builder.like(root.get("theater.name"), likeStr(filter.getTheaterName())));
            }

            if (!predicates.isEmpty())
                query.where(predicates.toArray(new Predicate[0]));

            return session.createQuery(query).getResultList();
        }
    }
}