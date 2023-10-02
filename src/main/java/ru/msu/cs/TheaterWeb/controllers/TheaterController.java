package ru.msu.cs.TheaterWeb.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.msu.cs.TheaterWeb.DAO.PlaceDAO;
import ru.msu.cs.TheaterWeb.DAO.TheaterDAO;
import ru.msu.cs.TheaterWeb.DAO.impl.PlaceDAOImpl;
import ru.msu.cs.TheaterWeb.DAO.impl.TheaterDAOImpl;
import ru.msu.cs.TheaterWeb.entities.Place;
import ru.msu.cs.TheaterWeb.entities.PlaceType;
import ru.msu.cs.TheaterWeb.entities.Theater;
import org.springframework.ui.Model;

import java.util.List;

@Controller
public class TheaterController {
    @Autowired
    private final PlaceDAO placeDAO = new PlaceDAOImpl();

    @Autowired
    private final TheaterDAO theaterDAO = new TheaterDAOImpl();

    @GetMapping("/theater")
    public String theaterPage(@RequestParam(name = "theater_id") Long theaterId, Model model) {
        Theater theater = theaterDAO.getById(theaterId);

        if (theater == null) {
            model.addAttribute("error_msg", "В кассе нет театра с ID " + theaterId);
            return "errorPage";
        }

        List<Place> placeList = placeDAO.getByFilter(PlaceDAO.Filter.builder().theaterId(theater.getId()).build());

        model.addAttribute("theater", theater);
        model.addAttribute("placeList", placeList);
        model.addAttribute("PlaceType", PlaceType.class);
        return "theater";
    }

    @GetMapping("/theaterList")
    public String theaterListPage(@RequestParam(name = "filter_theater_name", required = false) String filterTheaterName,
                                  @RequestParam(name = "filter_theater_address", required = false) String filterTheaterAddress,
                                  Model model) {
        TheaterDAO.Filter theaterFilter = TheaterDAO.Filter.builder()
                .theaterName(filterTheaterName)
                .theaterAddress(filterTheaterAddress)
                .build();

        List<Theater> theaterList;
        if (theaterFilter.isEmpty()) {
            theaterList = theaterDAO.getAll();
        } else {
            theaterList = theaterDAO.getByFilter(theaterFilter);
        }

        model.addAttribute("theaterFilter", theaterFilter);
        model.addAttribute("theaterList", theaterList);
        return "theaterList";
    }
    @GetMapping("/theaterEdit")
    public String theaterEditPage(@RequestParam(name = "theater_id", required = false) Long theaterId, Model model) {
        if (theaterId == null) {
            model.addAttribute("theater", new Theater());
            return "theaterEdit";
        }

        Theater theater = theaterDAO.getById(theaterId);

        if (theater == null) {
            model.addAttribute("error_msg", "В кассе нет театра с ID " + theaterId);
            return "errorPage";
        }

        model.addAttribute("theater", theater);
        return "theaterEdit";
    }

    @PostMapping(value = "/theaterSave")
    public String theaterSavePage(@RequestParam(name = "theater_id", required = false) Long theaterId,
                                  @RequestParam(name = "theater_name") String theaterName,
                                  @RequestParam(name = "theater_address") String theaterAddress,
                                  @RequestParam(name = "theater_info", required = false) String theaterInfo,
                                  Model model) {
        Theater theater = theaterDAO.getById(theaterId);

        if (theaterName.isEmpty()) {
            model.addAttribute("error_msg", "Театр должен иметь непустое название");
            return "errorPage";
        }
        if (theaterAddress.isEmpty()) {
            model.addAttribute("error_msg", "Театр должен иметь непустой адрес");
            return "errorPage";
        }

        if (theater != null) {
            theater.setName(theaterName);
            theater.setAddress(theaterAddress);
            theater.setInfo(theaterInfo);
            theaterDAO.update(theater);
        } else {
            theater = new Theater(theaterName, theaterAddress);
            theater.setInfo(theaterInfo);
            theaterDAO.save(theater);
        }
        return "redirect:/theater?theater_id=" + theater.getId();
    }
    @PostMapping("/theaterDelete")
    public String theaterDeletePage(@RequestParam(name = "theater_id") Long theaterId) {
        theaterDAO.deleteById(theaterId);
        return "redirect:/theaterList";
    }
}
