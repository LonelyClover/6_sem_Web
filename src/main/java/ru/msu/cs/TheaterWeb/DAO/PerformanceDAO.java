package ru.msu.cs.TheaterWeb.DAO;

import lombok.Getter;
import ru.msu.cs.TheaterWeb.entities.Performance;

import java.time.LocalDateTime;
import java.util.List;

public interface PerformanceDAO extends CommonDAO<Performance> {
    @Getter
    class Filter {
        private LocalDateTime datetime;
    }

    public List<Performance> getByFilter(Filter filter);
}
