package ru.msu.cs.TheaterWeb.DAO;

import lombok.Builder;
import lombok.Getter;
import ru.msu.cs.TheaterWeb.entities.Performance;

import java.time.LocalDate;
import java.util.List;

public interface PerformanceDAO extends CommonDAO<Performance> {
    @Getter
    @Builder
    class Filter extends CommonFilter {
        private LocalDate date;
        private Long playId;
    }

    List<Performance> getByFilter(Filter filter);
}
