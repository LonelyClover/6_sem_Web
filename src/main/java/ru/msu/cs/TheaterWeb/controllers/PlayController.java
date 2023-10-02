package ru.msu.cs.TheaterWeb.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.msu.cs.TheaterWeb.DAO.*;
import ru.msu.cs.TheaterWeb.DAO.impl.*;
import ru.msu.cs.TheaterWeb.entities.Actor;
import ru.msu.cs.TheaterWeb.entities.Performance;
import ru.msu.cs.TheaterWeb.entities.Play;
import ru.msu.cs.TheaterWeb.entities.Role;

import java.time.Duration;
import java.util.List;

@Controller
public class PlayController {
    @Autowired
    private final PlayDAO playDAO = new PlayDAOImpl();

    @Autowired
    private final RoleDAO roleDAO = new RoleDAOImpl();

    @Autowired
    private final ActorDAO actorDAO = new ActorDAOImpl();

    @Autowired
    private final DirectorDAO directorDAO = new DirectorDAOImpl();

    @Autowired
    private final PerformanceDAO performanceDAO = new PerformanceDAOImpl();

    @GetMapping("/play")
    public String playPage(@RequestParam(name = "play_id") Long playId, Model model) {
        Play play = playDAO.getById(playId);

        if (play == null) {
            model.addAttribute("error_msg", "В кассе нет пьесы с ID " + playId);
            return "errorPage";
        }

        List<Role> roleList = roleDAO.getByFilter(RoleDAO.Filter.builder().playId(playId).build());
        List<Actor> actorList = actorDAO.getByFilter(ActorDAO.Filter.builder().theaterId(play.getTheater().getId()).build());
        List<Performance> performanceList = performanceDAO.getByFilter(PerformanceDAO.Filter.builder().playId(playId).build());

        model.addAttribute("play", play);
        model.addAttribute("roleList", roleList);
        model.addAttribute("actorList", actorList);
        model.addAttribute("performanceList", performanceList);
        return "play";
    }

    @GetMapping("/playList")
    public String playListPage(@RequestParam(name = "filter_play_name", required = false) String filterPlayName,
                               @RequestParam(name = "filter_theater_name", required = false) String filterTheaterName,
                               @RequestParam(name = "filter_theater_id", required = false) Long filterTheaterId,
                               @RequestParam(name = "filter_actor_id", required = false) Long filterActorId,
                               @RequestParam(name = "filter_director_id", required = false) Long filterDirectorId,
                               Model model) {
        PlayDAO.Filter playFilter = PlayDAO.Filter.builder()
                .playName(filterPlayName)
                .theaterName(filterTheaterName)
                .theaterId(filterTheaterId)
                .actorId(filterActorId)
                .directorId(filterDirectorId)
                .build();

        List<Play> playList;
        if (playFilter.isEmpty()) {
            playList = playDAO.getAll();
        } else {
            playList = playDAO.getByFilter(playFilter);
        }

        model.addAttribute("playFilter", playFilter);
        model.addAttribute("playList", playList);
        return "playList";
    }

    @GetMapping("/playEdit")
    public String playEditPage(@RequestParam(name = "play_id", required = false) Long playId, Model model) {
        if (playId == null) {
            model.addAttribute("play", new Play());
            model.addAttribute("directorService", directorDAO);
            model.addAttribute("Filter", DirectorDAO.Filter.class);
            return "playEdit";
        }

        Play play = playDAO.getById(playId);

        if (play == null) {
            model.addAttribute("error_msg", "В кассе нет пьесы с ID " + playId);
            return "errorPage";
        }

        model.addAttribute("play", play);
        model.addAttribute("directorService", directorDAO);
        model.addAttribute("Filter", DirectorDAO.Filter.class);
        return "playEdit";
    }

    @PostMapping(value = "/playSave")
    public String playSavePage(@RequestParam(name = "play_id", required = false) Long playId,
                               @RequestParam(name = "play_name") String playName,
                               @RequestParam(name = "play_duration_string") String playDurationString,
                               @RequestParam(name = "director_id") Long directorId,
                               @RequestParam(name = "play_info", required = false) String playInfo,
                               Model model) {
        Play play = playDAO.getById(playId);

        if (playName.isEmpty()) {
            model.addAttribute("error_msg", "Пьеса должна иметь непустое название");
            return "errorPage";
        }

        Duration playDuration;
        try {
            String[] tokens = playDurationString.split(":");
            long hours = Long.parseLong(tokens[0]);
            long minutes = Long.parseLong(tokens[1]);
            assert tokens.length == 2;
            playDuration = Duration.ofHours(hours).plusMinutes(minutes);
        } catch (Exception e) {
            model.addAttribute("error_msg", "Длительность пьесы должна быть в формате HH:mm");
            return "errorPage";
        }

        if (play != null) {
            play.setName(playName);
            play.setDuration(playDuration);
            play.setInfo(playInfo);
            play.setTheater(directorDAO.getById(directorId).getTheater());
            play.setDirector(directorDAO.getById(directorId));
            playDAO.update(play);
        } else {
            play = new Play(playName, playDuration, directorDAO.getById(directorId).getTheater(), directorDAO.getById(directorId));
            play.setInfo(playInfo);
            playDAO.save(play);
        }
        return "redirect:/play?play_id=" + play.getId();
    }
    @PostMapping("/playDelete")
    public String playDeletePage(@RequestParam(name = "play_id") Long playId) {
        playDAO.deleteById(playId);
        return "redirect:/playList";
   }
}
