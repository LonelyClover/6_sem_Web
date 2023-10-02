package ru.msu.cs.TheaterWeb.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.msu.cs.TheaterWeb.DAO.ActorDAO;
import ru.msu.cs.TheaterWeb.DAO.TheaterDAO;
import ru.msu.cs.TheaterWeb.DAO.impl.ActorDAOImpl;
import ru.msu.cs.TheaterWeb.DAO.impl.TheaterDAOImpl;
import ru.msu.cs.TheaterWeb.entities.Actor;

import javax.annotation.Nullable;
import java.util.List;

@Controller
public class ActorController {
    @Autowired
    private final ActorDAO actorDAO = new ActorDAOImpl();

    @Autowired
    private final TheaterDAO theaterDAO = new TheaterDAOImpl();

    @GetMapping("/actor")
    public String actorPage(@RequestParam(name = "actor_id") Long actorId, Model model) {
        Actor actor = actorDAO.getById(actorId);

        if (actor == null) {
            model.addAttribute("error_msg", "В кассе нет актера с ID " + actorId);
            return "errorPage";
        }

        model.addAttribute("actor", actor);
        return "actor";
    }

    @GetMapping("/actorList")
    public String actorListPage(@RequestParam(name = "filter_actor_name", required = false) String filterActorName,
                                @RequestParam(name = "filter_theater_name", required = false) String filterTheaterName,
                                @RequestParam(name = "filter_theater_id", required = false) Long filterTheaterId,
                                Model model) {
        ActorDAO.Filter actorFilter = ActorDAO.Filter.builder()
                .actorName(filterActorName)
                .theaterName(filterTheaterName)
                .theaterId(filterTheaterId)
                .build();

        List<Actor> actorList;
        if (actorFilter.isEmpty()) {
            actorList = actorDAO.getAll();
        } else {
            actorList = actorDAO.getByFilter(actorFilter);
        }

        model.addAttribute("actorFilter", actorFilter);
        model.addAttribute("actorList", actorList);
        return "actorList";
    }
    @GetMapping("/actorEdit")
    public String actorEditPage(@RequestParam(name = "actor_id", required = false) Long actorId, Model model) {
        if (actorId == null) {
            model.addAttribute("actor", new Actor());
            model.addAttribute("theaterService", theaterDAO);
            return "actorEdit";
        }

        Actor actor = actorDAO.getById(actorId);

        if (actor == null) {
            model.addAttribute("error_msg", "В кассе нет актера с ID " + actorId);
            return "errorPage";
        }

        model.addAttribute("actor", actor);
        model.addAttribute("theaterService", theaterDAO);
        return "actorEdit";
    }

    @PostMapping(value = "/actorSave")
    public String actorSavePage(@RequestParam(name = "actor_id", required = false) Long actorId,
                                @RequestParam(name = "actor_name") String actorName,
                                @RequestParam(name = "actor_info", required = false) String actorInfo,
                                @RequestParam(name = "theater_id") @Nullable Long theaterId,
                                Model model) {
        Actor actor = actorDAO.getById(actorId);

        if (actorName.isEmpty()) {
            model.addAttribute("error_msg", "Актер должен иметь непустое имя");
            return "errorPage";
        }
        if (theaterId == null) {
            model.addAttribute("error_msg", "Актер должен принадлежать театру");
            return "errorPage";
        }

        if (actor != null) {
            actor.setName(actorName);
            actor.setTheater(theaterDAO.getById(theaterId));
            actor.setInfo(actorInfo);
            actorDAO.update(actor);
        } else {
            actor = new Actor(actorName, theaterDAO.getById(theaterId));
            actor.setInfo(actorInfo);
            actorDAO.save(actor);
        }
        return "redirect:/actor?actor_id=" + actor.getId();
    }
    @PostMapping("/actorDelete")
    public String actorDeletePage(@RequestParam(name = "actor_id") Long actorId) {
        actorDAO.deleteById(actorId);
        return "redirect:/actorList";
    }
}
