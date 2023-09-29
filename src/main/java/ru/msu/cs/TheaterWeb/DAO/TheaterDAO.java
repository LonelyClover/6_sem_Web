package ru.msu.cs.TheaterWeb.DAO;

import lombok.Getter;
import ru.msu.cs.TheaterWeb.entities.Theater;

import java.util.List;

public interface TheaterDAO extends CommonDAO<Theater> {
    @Getter
    class Filter {
        private String theaterName;
        private String theaterAddress;
    }

    public List<Theater> getByFilter(Filter filter);
}