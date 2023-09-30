package ru.msu.cs.TheaterWeb.DAO.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import ru.msu.cs.TheaterWeb.DAO.CommonDAO;
import ru.msu.cs.TheaterWeb.entities.CommonEntity;

import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

@Repository
public abstract class CommonDAOImpl<Entity extends CommonEntity> implements CommonDAO<Entity> {
    protected SessionFactory sessionFactory;

    protected Class<Entity> persistentClass;

    public CommonDAOImpl(Class<Entity> entityClass) {
        this.persistentClass = entityClass;
    }

    @Autowired
    public void setSessionFactory(LocalSessionFactoryBean sessionFactory) {
        this.sessionFactory = sessionFactory.getObject();
    }

    @Override
    public Entity getById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(persistentClass, id);
        }
    }

    @Override
    public List<Entity> getAll() {
        try (Session session = sessionFactory.openSession()) {
            CriteriaQuery<Entity> query = session.getCriteriaBuilder().createQuery(persistentClass);
            query.from(persistentClass);
            return session.createQuery(query).getResultList();
        }
    }

    @Override
    public void save(Entity entity) {
        try (Session session = sessionFactory.openSession()) {
            if (entity.getId() != null) {
                entity.setId(null);
            }
            session.beginTransaction();
            session.saveOrUpdate(entity);
            session.getTransaction().commit();
        }
    }

    @Override
    public void saveAll(List<Entity> entities) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            for (Entity entity : entities) {
                this.save(entity);
            }
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(Entity entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(entity);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(Entity entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(entity);
            session.getTransaction().commit();
        }
    }

    protected String likeStr(String str) {
        return "%" + str + "%";
    }
}
