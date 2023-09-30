package ru.msu.cs.TheaterWeb.DAO;

import lombok.Builder;
import lombok.Getter;
import ru.msu.cs.TheaterWeb.entities.Director;

import java.util.List;

public interface DirectorDAO extends CommonDAO<Director> {
    @Getter
    @Builder
    class Filter {
        private String directorName;
        private String theaterName;
    }

    List<Director> getByFilter(Filter filter);
}
