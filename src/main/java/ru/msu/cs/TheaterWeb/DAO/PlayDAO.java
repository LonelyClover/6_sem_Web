package ru.msu.cs.TheaterWeb.DAO;

import lombok.Builder;
import lombok.Getter;
import ru.msu.cs.TheaterWeb.entities.Play;

import java.util.List;

public interface PlayDAO extends CommonDAO<Play> {
    @Getter
    @Builder
    class Filter {
        private String playName;
        private String theaterName;
    }

    List<Play> getByFilter(Filter filter);
}