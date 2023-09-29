package ru.msu.cs.TheaterWeb.DAO;

import lombok.Getter;
import ru.msu.cs.TheaterWeb.entities.Play;

import java.util.List;

public interface PlayDAO extends CommonDAO<Play> {
    @Getter
    class Filter {
        private String playName;
        private String theaterName;
    }

    public List<Play> getByFilter(Filter filter);
}