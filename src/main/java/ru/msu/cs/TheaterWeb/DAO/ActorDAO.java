package ru.msu.cs.TheaterWeb.DAO;

import lombok.Builder;
import lombok.Getter;
import ru.msu.cs.TheaterWeb.entities.Actor;

import java.util.List;

public interface ActorDAO extends CommonDAO<Actor> {
    @Getter
    @Builder
    class Filter {
        private String actorName;
        private String theaterName;
    }

    List<Actor> getByFilter(Filter filter);
}