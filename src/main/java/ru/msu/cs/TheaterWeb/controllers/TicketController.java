package ru.msu.cs.TheaterWeb.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.msu.cs.TheaterWeb.DAO.*;
import ru.msu.cs.TheaterWeb.DAO.impl.*;
import ru.msu.cs.TheaterWeb.entities.Place;
import ru.msu.cs.TheaterWeb.entities.Ticket;
import ru.msu.cs.TheaterWeb.entities.PlaceType;
import ru.msu.cs.TheaterWeb.entities.TicketPrice;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TicketController {
    @Autowired
    private final TicketDAO ticketDAO = new TicketDAOImpl();

    @Autowired
    private final TicketPriceDAO ticketPriceDAO = new TicketPriceDAOImpl();

    @Autowired
    private final PlaceDAO placeDAO = new PlaceDAOImpl();

    @Autowired
    private final PerformanceDAO performanceDAO = new PerformanceDAOImpl();


    @GetMapping("/ticketList")
    public String ticketListPage(@RequestParam(name = "filter_ticket_id", required = false) Long filterTicketId,
                                 @RequestParam(name = "filter_customer_name", required = false) String filterCustomerName,
                                 @RequestParam(name = "filter_customer_phone_number", required = false) String filterCustomerPhoneNumber,
                                 @RequestParam(name = "filter_theater_name", required = false) String filterTheaterName,
                                 @RequestParam(name = "filter_play_name", required = false) String filterPlayName,
                                 @RequestParam(name = "filter_date", required = false) String filterDateString,
                                 Model model) {
        TicketDAO.Filter ticketFilter = TicketDAO.Filter.builder()
                .ticketId(filterTicketId)
                .customerName(filterCustomerName)
                .customerPhoneNumber(filterCustomerPhoneNumber)
                .theaterName(filterTheaterName)
                .playName(filterPlayName)
                .date(filterDateString != null && !filterDateString.isEmpty() ? LocalDate.parse(filterDateString) : null)
                .build();

        List<Ticket> ticketList = ticketDAO.getByFilter(ticketFilter);

        model.addAttribute("ticketFilter", ticketFilter);
        model.addAttribute("ticketList", ticketList);
        return "ticketList";
    }

    @GetMapping("/ticketBuy")
    public String ticketBuyPage(@RequestParam(name = "performance_id") Long performanceId,
                                Model model) {
        List<TicketPrice> ticketPriceList = ticketPriceDAO.getByFilter(TicketPriceDAO.Filter.builder().performanceId(performanceId).build());

        Map<PlaceType, Long> ticketPriceMap = new HashMap<>();
        for (TicketPrice ticketPrice : ticketPriceList) {
            ticketPriceMap.put(ticketPrice.getPlaceType(), ticketPrice.getPrice());
        }

        List<Place> placeListAll = placeDAO.getByFilter(PlaceDAO.Filter.builder().theaterId(performanceDAO.getById(performanceId).getPlay().getTheater().getId()).build());
        List<Ticket> ticketList = ticketDAO.getByFilter(TicketDAO.Filter.builder().performanceId(performanceId).build());
        List<Long> placeIdListOccupied = ticketList.stream().map(ticket -> ticket.getPlace().getId()).toList();
        List<Place> placeListFree = placeListAll.stream().filter(place -> !placeIdListOccupied.contains(place.getId())).toList();
        model.addAttribute("performanceId", performanceId);
        model.addAttribute("ticketPriceMap", ticketPriceMap);
        model.addAttribute("placeList", placeListFree);
        return "ticketBuy";
    }

    @PostMapping("/ticketSave")
    public String ticketSavePage(@RequestParam(name = "performance_id") Long perforamnceId,
                                 @RequestParam(name = "place_id") Long placeId,
                                 @RequestParam(name = "customer_name") String customerName,
                                 @RequestParam(name = "customer_phone_number") String customerPhoneNumber) {
        Ticket ticket = new Ticket(
                performanceDAO.getById(perforamnceId),
                placeDAO.getById(placeId),
                customerName,
                customerPhoneNumber);

        ticketDAO.save(ticket);

        return "redirect:/ticketSuccess?ticket_id=" + ticket.getId();
    }

    @GetMapping("ticketSuccess")
    public String ticketSuccessPage(@RequestParam(name = "ticket_id") Long ticketId, Model model) {
        model.addAttribute("ticketId", ticketId);
        return "ticketSuccess";
    }

    @PostMapping("/ticketDelete")
    public String ticketDeletePage(@RequestParam(name = "ticket_id") Long ticketId) {
        ticketDAO.deleteById(ticketId);
        return "redirect:/ticketList";
   }
}
