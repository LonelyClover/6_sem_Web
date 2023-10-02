package ru.msu.cs.TheaterWeb.DAO;

import lombok.Builder;
import lombok.Getter;
import ru.msu.cs.TheaterWeb.entities.TicketPrice;

import java.util.List;

public interface TicketPriceDAO extends CommonDAO<TicketPrice> {
    @Getter
    @Builder
    class Filter extends CommonFilter {
        private Long performanceId;
    }

    List<TicketPrice> getByFilter(Filter filter);
}