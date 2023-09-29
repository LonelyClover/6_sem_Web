package ru.msu.cs.TheaterWeb.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.msu.cs.TheaterWeb.DAO.impl.TheaterDAOImpl;
import ru.msu.cs.TheaterWeb.entities.Theater;
import org.springframework.ui.Model;

import java.util.List;

@Controller
public class TheaterController {
    @Autowired
    private TheaterDAOImpl theaterDAO;

    @GetMapping("/theaterList")
    public String getTheaterList(Model model) {
        List<Theater> theaterList= theaterDAO.getAll();
        model.addAttribute(theaterList);
        return "theaterList";
    }
}
