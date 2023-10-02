package ru.msu.cs.TheaterWeb.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.msu.cs.TheaterWeb.DAO.TicketPriceDAO;
import ru.msu.cs.TheaterWeb.DAO.impl.TicketPriceDAOImpl;
import ru.msu.cs.TheaterWeb.entities.TicketPrice;

@Controller
public class TicketPriceController {
    @Autowired
    private final TicketPriceDAO ticketPriceDAO = new TicketPriceDAOImpl();

    @PostMapping("/ticketPriceEdit")
    public String ticketPriceEditPage(@RequestParam(name = "ticket_price_id") Long ticketPriceId,
                                      @RequestParam(name = "performance_id") Long performanceId,
                                      @RequestParam(name = "price", required = false) Long price,
                                      Model model) {
        if (price == null) {
            model.addAttribute("error_msg", "Стоимоть билета должна быть непустой");
            return "errorPage";
        }

        TicketPrice ticketPrice = ticketPriceDAO.getById(ticketPriceId);
        ticketPrice.setPrice(price);
        ticketPriceDAO.update(ticketPrice);
        return "redirect:/performance?performance_id=" + performanceId;
    }
}