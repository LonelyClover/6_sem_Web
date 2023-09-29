package ru.msu.cs.TheaterWeb.DAO;

import lombok.Getter;
import ru.msu.cs.TheaterWeb.entities.Place;
import ru.msu.cs.TheaterWeb.entities.PlaceType;

import java.util.List;

public interface PlaceDAO extends CommonDAO<Place> {
    @Getter
    class Filter {
        private String theaterName;
        private PlaceType placeType;
    }

    public List<Place> getByFilter(Filter filter);
}