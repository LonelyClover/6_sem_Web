package ru.msu.cs.TheaterWeb.DAO;

import lombok.Builder;
import ru.msu.cs.TheaterWeb.entities.CommonEntity;

import java.util.List;

public interface CommonDAO<Entity extends CommonEntity> {
    Entity getById(Long id);

    List<Entity> getAll();

    void save(Entity entity);

    void saveAll(List<Entity> entities);

    void update(Entity entity);

    void delete(Entity entity);
}
