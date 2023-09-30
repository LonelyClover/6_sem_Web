package ru.msu.cs.TheaterWeb.DAO.impl;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import ru.msu.cs.TheaterWeb.DAO.DirectorDAO;
import ru.msu.cs.TheaterWeb.entities.Director;
import ru.msu.cs.TheaterWeb.entities.Theater;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DirectorDAOImpl extends CommonDAOImpl<Director> implements DirectorDAO {
    public DirectorDAOImpl() {
        super(Director.class);
    }
    @Override
    public List<Director> getByFilter(Filter filter) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Director> query = builder.createQuery(Director.class);
            Root<Director> root = query.from(Director.class);
            Join<Director, Theater> join = root.join("theater");

            List<Predicate> predicates = new ArrayList<>();
            if (filter.getDirectorName() != null) {
                predicates.add(builder.like(root.get("name"), likeStr(filter.getDirectorName())));
            }
            if (filter.getTheaterName() != null) {
                predicates.add(builder.like(join.get("name"), likeStr(filter.getTheaterName())));
            }

            if (!predicates.isEmpty())
                query.where(predicates.toArray(new Predicate[0]));

            return session.createQuery(query).getResultList();
        }
    }
}