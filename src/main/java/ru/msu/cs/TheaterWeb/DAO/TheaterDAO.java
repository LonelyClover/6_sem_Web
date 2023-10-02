package ru.msu.cs.TheaterWeb.DAO;

import lombok.Builder;
import lombok.Getter;
import ru.msu.cs.TheaterWeb.entities.Theater;

import java.util.List;

public interface TheaterDAO extends CommonDAO<Theater> {
    @Getter
    @Builder
    class Filter extends CommonFilter {
        private String theaterName;
        private String theaterAddress;
    }

    List<Theater> getByFilter(Filter filter);
}