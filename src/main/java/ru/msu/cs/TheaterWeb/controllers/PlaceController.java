package ru.msu.cs.TheaterWeb.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.msu.cs.TheaterWeb.DAO.PlaceDAO;
import ru.msu.cs.TheaterWeb.DAO.TheaterDAO;
import ru.msu.cs.TheaterWeb.DAO.impl.PlaceDAOImpl;
import ru.msu.cs.TheaterWeb.DAO.impl.TheaterDAOImpl;
import ru.msu.cs.TheaterWeb.entities.Place;
import ru.msu.cs.TheaterWeb.entities.PlaceType;

@Controller
public class PlaceController {
    @Autowired
    private final PlaceDAO placeDAO = new PlaceDAOImpl();

    @Autowired
    private final TheaterDAO theaterDAO = new TheaterDAOImpl();

    @PostMapping("/placeAdd")
    public String placeAddPage(@RequestParam(name = "theater_id") Long theaterId,
                               @RequestParam(name = "place_number") String placeNumber,
                               @RequestParam(name = "place_type") Long placeTypeValue,
                               Model model) {
        if (placeNumber.isEmpty()) {
            model.addAttribute("error_msg", "Место должно иметь непустой номер");
            return "errorPage";
        }

        Place place = new Place(placeNumber, PlaceType.fromLong(placeTypeValue), theaterDAO.getById(theaterId));
        placeDAO.save(place);
        return "redirect:/theater?theater_id=" + theaterId;
    }
    @PostMapping("/placeEdit")
    public String placeEditPage(@RequestParam(name = "theater_id") Long theaterId,
                                @RequestParam(name = "place_id") Long placeId,
                                @RequestParam(name = "place_number") String placeNumber,
                                Model model) {
        if (placeNumber.isEmpty()) {
            model.addAttribute("error_msg", "Место должно иметь непустой номер");
            return "errorPage";
        }

        Place place = placeDAO.getById(placeId);
        place.setNumber(placeNumber);
        placeDAO.update(place);
        return "redirect:/theater?theater_id=" + theaterId;
    }
    @PostMapping("/placeDelete")
    public String placeDeletePage(@RequestParam(name = "theater_id") Long theaterId,
                                  @RequestParam(name = "place_id") Long placeId) {
        placeDAO.deleteById(placeId);
        return "redirect:/theater?theater_id=" + theaterId;
    }

}