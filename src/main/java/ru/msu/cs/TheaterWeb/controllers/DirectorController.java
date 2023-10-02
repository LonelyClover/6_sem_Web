package ru.msu.cs.TheaterWeb.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.msu.cs.TheaterWeb.DAO.DirectorDAO;
import ru.msu.cs.TheaterWeb.DAO.TheaterDAO;
import ru.msu.cs.TheaterWeb.DAO.impl.DirectorDAOImpl;
import ru.msu.cs.TheaterWeb.DAO.impl.TheaterDAOImpl;
import ru.msu.cs.TheaterWeb.entities.Director;

import javax.annotation.Nullable;
import java.util.List;

@Controller
public class DirectorController {
    @Autowired
    private final DirectorDAO directorDAO = new DirectorDAOImpl();

    @Autowired
    private final TheaterDAO theaterDAO = new TheaterDAOImpl();

    @GetMapping("/director")
    public String directorPage(@RequestParam(name = "director_id") Long directorId, Model model) {
        Director director = directorDAO.getById(directorId);

        if (director == null) {
            model.addAttribute("error_msg", "В кассе нет актера с ID " + directorId);
            return "errorPage";
        }

        model.addAttribute("director", director);
        return "director";
    }

    @GetMapping("/directorList")
    public String directorListPage(@RequestParam(name = "filter_director_name", required = false) String filterDirectorName,
                                @RequestParam(name = "filter_theater_name", required = false) String filterTheaterName,
                                @RequestParam(name = "filter_theater_id", required = false) Long filterTheaterId,
                                Model model) {
        DirectorDAO.Filter directorFilter = DirectorDAO.Filter.builder()
                .directorName(filterDirectorName)
                .theaterName(filterTheaterName)
                .theaterId(filterTheaterId)
                .build();

        List<Director> directorList;
        if (directorFilter.isEmpty()) {
            directorList = directorDAO.getAll();
        } else {
            directorList = directorDAO.getByFilter(directorFilter);
        }

        model.addAttribute("directorFilter", directorFilter);
        model.addAttribute("directorList", directorList);
        return "directorList";
    }
    @GetMapping("/directorEdit")
    public String directorEditPage(@RequestParam(name = "director_id", required = false) Long directorId, Model model) {
        if (directorId == null) {
            model.addAttribute("director", new Director());
            model.addAttribute("theaterService", theaterDAO);
            return "directorEdit";
        }

        Director director = directorDAO.getById(directorId);

        if (director == null) {
            model.addAttribute("error_msg", "В кассе нет режиссера с ID " + directorId);
            return "errorPage";
        }

        model.addAttribute("director", director);
        model.addAttribute("theaterService", theaterDAO);
        return "directorEdit";
    }

    @PostMapping(value = "/directorSave")
    public String directorSavePage(@RequestParam(name = "director_id", required = false) Long directorId,
                                @RequestParam(name = "director_name") String directorName,
                                @RequestParam(name = "director_info", required = false) String directorInfo,
                                @RequestParam(name = "theater_id") @Nullable Long theaterId,
                                Model model) {
        Director director = directorDAO.getById(directorId);

        if (directorName.isEmpty()) {
            model.addAttribute("error_msg", "Режиссер должен иметь непустое имя");
            return "errorPage";
        }
        if (theaterId == null) {
            model.addAttribute("error_msg", "Режиссер должен принадлежать театру");
            return "errorPage";
        }

        if (director != null) {
            director.setName(directorName);
            director.setTheater(theaterDAO.getById(theaterId));
            director.setInfo(directorInfo);
            directorDAO.update(director);
        } else {
            director = new Director(directorName, theaterDAO.getById(theaterId));
            director.setInfo(directorInfo);
            directorDAO.save(director);
        }
        return "redirect:/director?director_id=" + director.getId();
    }
    @PostMapping("/directorDelete")
    public String directorDeletePage(@RequestParam(name = "director_id") Long directorId) {
        directorDAO.deleteById(directorId);
        return "redirect:/directorList";
    }
}
