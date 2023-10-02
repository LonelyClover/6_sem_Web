package ru.msu.cs.TheaterWeb.DAO.impl;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import ru.msu.cs.TheaterWeb.DAO.PlayDAO;
import ru.msu.cs.TheaterWeb.entities.*;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
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
            Join<Play, Theater> joinTheater = root.join("theater");
            Join<Play, Director> joinDirector = root.join("director");

            List<Predicate> predicates = new ArrayList<>();
            if (filter.getPlayName() != null) {
                predicates.add(builder.like(root.get("name"), likeStr(filter.getPlayName())));
            }
            if (filter.getTheaterName() != null) {
                predicates.add(builder.like(joinTheater.get("name"), likeStr(filter.getTheaterName())));
            }
            if (filter.getTheaterId() != null) {
                predicates.add(builder.equal(joinTheater.get("id"), filter.getTheaterId()));
            }
            if (filter.getDirectorId() != null) {
                predicates.add(builder.equal(joinDirector.get("id"), filter.getDirectorId()));
            }
            if (filter.getActorId() != null) {
                @SuppressWarnings("unchecked")
                List<Long> filteredList = session.createSQLQuery(
                        "SELECT play.id FROM role " +
                                "JOIN actor ON actor.id = role.actor_id " +
                                "JOIN play ON play.id = role.play_id " +
                                "WHERE actor.id = :actor_id ")
                        .setParameter("actor_id", filter.getActorId())
                        .getResultList();
                predicates.add(root.get("id").in(filteredList));
            }

            if (!predicates.isEmpty())
                query.where(predicates.toArray(new Predicate[0]));

            return session.createQuery(query).getResultList();
        }
    }
}