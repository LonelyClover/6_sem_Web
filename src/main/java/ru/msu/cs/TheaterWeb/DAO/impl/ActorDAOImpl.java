package ru.msu.cs.TheaterWeb.DAO.impl;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import ru.msu.cs.TheaterWeb.DAO.ActorDAO;
import ru.msu.cs.TheaterWeb.entities.Actor;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ActorDAOImpl extends CommonDAOImpl<Actor> implements ActorDAO {
    public ActorDAOImpl() {
        super(Actor.class);
    }
    @Override
    public List<Actor> getByFilter(Filter filter) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Actor> query = builder.createQuery(Actor.class);
            Root<Actor> root = query.from(Actor.class);

            List<Predicate> predicates = new ArrayList<>();
            if (filter.getActorName() != null) {
                predicates.add(builder.like(root.get("name"), likeStr(filter.getActorName())));
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