package ru.msu.cs.TheaterWeb.DAO.impl;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import ru.msu.cs.TheaterWeb.DAO.TheaterDAO;
import ru.msu.cs.TheaterWeb.entities.Theater;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TheaterDAOImpl extends CommonDAOImpl<Theater> implements TheaterDAO {
    public TheaterDAOImpl() {
        super(Theater.class);
    }

    @Override
    public List<Theater> getByFilter(Filter filter) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Theater> query = builder.createQuery(Theater.class);
            Root<Theater> root = query.from(Theater.class);

            List<Predicate> predicates = new ArrayList<>();
            if (filter.getTheaterName() != null) {
                predicates.add(builder.like(root.get("name"), likeStr(filter.getTheaterName())));
            }
            if (filter.getTheaterAddress() != null) {
                predicates.add(builder.like(root.get("address"), likeStr(filter.getTheaterAddress())));
            }

            if (!predicates.isEmpty()) {
                query.where(predicates.toArray(new Predicate[0]));
            }

            return session.createQuery(query).getResultList();
        }
    }
}