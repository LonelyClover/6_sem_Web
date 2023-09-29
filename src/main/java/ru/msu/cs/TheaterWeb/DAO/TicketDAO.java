package ru.msu.cs.TheaterWeb.DAO;

import lombok.Getter;
import ru.msu.cs.TheaterWeb.entities.PlaceType;
import ru.msu.cs.TheaterWeb.entities.Ticket;

import java.time.LocalDateTime;
import java.util.List;

public interface TicketDAO extends CommonDAO<Ticket> {
    @Getter
    class Filter {
        private String customerName;
        private String customerPhoneNumber;
        private String theaterName;
        private String playName;
        private LocalDateTime datetime;
        private PlaceType placeType;
    }

    public List<Ticket> getByFilter(Filter filter);
}