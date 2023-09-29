package ru.msu.cs.TheaterWeb.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping(value = {"/", "/index"})
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/theaterList")
    public String theaterList() {
        return "theaterList";
    }

    @RequestMapping(value = "/actorList")
    public String actorList() {
        return "actorList";
    }

    @RequestMapping(value = "/directorList")
    public String directorList() {
        return "directorList";
    }

    @RequestMapping(value = "/playList")
    public String playList() {
        return "playList";
    }

    @RequestMapping(value = "/ticketList")
    public String ticketList() {
        return "ticketList";
    }
}
