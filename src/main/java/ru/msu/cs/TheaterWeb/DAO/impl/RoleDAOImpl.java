package ru.msu.cs.TheaterWeb.DAO.impl;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import ru.msu.cs.TheaterWeb.DAO.RoleDAO;
import ru.msu.cs.TheaterWeb.entities.Role;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RoleDAOImpl extends CommonDAOImpl<Role> implements RoleDAO {
    public RoleDAOImpl() {
        super(Role.class);
    }

    @Override
    public List<Role> getByFilter(Filter filter) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Role> query = builder.createQuery(Role.class);
            Root<Role> root = query.from(Role.class);

            List<Predicate> predicates = new ArrayList<>();
            if (filter.getActorId() != null) {
                predicates.add(builder.equal(root.get("actor"), filter.getActorId()));
            }
            if (filter.getPlayId() != null) {
                predicates.add(builder.equal(root.get("play"), filter.getPlayId()));
            }

            if (!predicates.isEmpty())
                query.where(predicates.toArray(new Predicate[0]));

            return session.createQuery(query).getResultList();
        }
    }
}