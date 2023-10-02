package ru.msu.cs.TheaterWeb.DAO.impl;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import ru.msu.cs.TheaterWeb.DAO.PerformanceDAO;
import ru.msu.cs.TheaterWeb.entities.Performance;
import ru.msu.cs.TheaterWeb.entities.Play;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PerformanceDAOImpl extends CommonDAOImpl<Performance> implements PerformanceDAO {
    public PerformanceDAOImpl() {
        super(Performance.class);
    }
    @Override
    public List<Performance> getByFilter(Filter filter) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Performance> query = builder.createQuery(Performance.class);
            Root<Performance> root = query.from(Performance.class);
            Join<Performance, Play> join = root.join("play");

            List<Predicate> predicates = new ArrayList<>();
            if (filter.getDate() != null) {
                predicates.add(builder.greaterThanOrEqualTo(root.get("datetime"), filter.getDate().atStartOfDay()));
                predicates.add(builder.lessThan(root.get("datetime"), filter.getDate().plusDays(1).atStartOfDay()));
            }
            if (filter.getPlayId() != null) {
                predicates.add(builder.equal(join.get("id"), filter.getPlayId()));
            }

            if (!predicates.isEmpty())
                query.where(predicates.toArray(new Predicate[0]));

            return session.createQuery(query).getResultList();
        }
    }
}