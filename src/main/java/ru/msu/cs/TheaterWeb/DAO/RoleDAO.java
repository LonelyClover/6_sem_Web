package ru.msu.cs.TheaterWeb.DAO;

import lombok.Builder;
import lombok.Getter;
import ru.msu.cs.TheaterWeb.entities.Role;

import java.util.List;

public interface RoleDAO extends CommonDAO<Role> {
    @Getter
    @Builder
    class Filter extends CommonFilter {
        private Long actorId;
        private Long playId;
    }

    List<Role> getByFilter(Filter filter);
}
