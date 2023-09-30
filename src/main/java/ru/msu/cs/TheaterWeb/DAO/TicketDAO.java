package ru.msu.cs.TheaterWeb.DAO;

import lombok.Builder;
import lombok.Getter;
import ru.msu.cs.TheaterWeb.entities.PlaceType;
import ru.msu.cs.TheaterWeb.entities.Ticket;

import java.time.LocalDate;
import java.util.List;

public interface TicketDAO extends CommonDAO<Ticket> {
    @Getter
    @Builder
    class Filter {
        private String customerName;
        private String customerPhoneNumber;
        private String theaterName;
        private String playName;
        private LocalDate date;
        private PlaceType placeType;
    }

    List<Ticket> getByFilter(Filter filter);
}