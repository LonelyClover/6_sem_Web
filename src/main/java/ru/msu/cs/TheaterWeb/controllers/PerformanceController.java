package ru.msu.cs.TheaterWeb.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.msu.cs.TheaterWeb.DAO.*;
import ru.msu.cs.TheaterWeb.DAO.impl.*;
import ru.msu.cs.TheaterWeb.entities.*;
import ru.msu.cs.TheaterWeb.entities.Performance;

import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PerformanceController {
    @Autowired
    private final PerformanceDAO performanceDAO = new PerformanceDAOImpl();

    @Autowired
    private final TicketPriceDAO ticketPriceDAO = new TicketPriceDAOImpl();

    @Autowired
    private final PlayDAO playDAO = new PlayDAOImpl();

    @GetMapping("/performance")
    public String performancePage(@RequestParam(name = "performance_id") Long performanceId, Model model) {
        Performance performance = performanceDAO.getById(performanceId);

        if (performance == null) {
            model.addAttribute("error_msg", "В кассе нет выступления с ID " + performanceId);
            return "errorPage";
        }

        List<TicketPrice> ticketPriceList = ticketPriceDAO.getByFilter(TicketPriceDAO.Filter.builder().performanceId(performanceId).build());

        model.addAttribute("performance", performance);
        model.addAttribute("ticketPriceList", ticketPriceList);
        return "performance";
    }
    @GetMapping("/performanceEdit")
    public String performanceEditPage(@RequestParam(name = "performance_id", required = false) Long performanceId,
                                      @RequestParam(name = "play_id") Long playId,
                                      Model model) {
        if (performanceId == null) {
            model.addAttribute("performance", new Performance());
            model.addAttribute("playId", playId);
            return "performanceEdit";
        }

        Performance performance = performanceDAO.getById(performanceId);

        if (performance == null) {
            model.addAttribute("error_msg", "В кассе нет пьесы с ID " + performanceId);
            return "errorPage";
        }

        List<TicketPrice> ticketPriceList = ticketPriceDAO.getByFilter(TicketPriceDAO.Filter.builder().performanceId(performanceId).build());
        Map<PlaceType, Long> ticketPriceMap = new HashMap<>();
        for (TicketPrice ticketPrice : ticketPriceList) {
            ticketPriceMap.put(ticketPrice.getPlaceType(), ticketPrice.getPrice());
        }

        model.addAttribute("performance", performance);
        model.addAttribute("playId", playId);
        model.addAttribute("ticketPriceMap", ticketPriceMap);
        model.addAttribute("PlaceType", PlaceType.class);
        return "performanceEdit";
    }

    @PostMapping(value = "/performanceSave")
    public String performanceSavePage(@RequestParam(name = "performance_id", required = false) Long performanceId,
                                      @RequestParam(name = "play_id") @Nullable Long playId,
                                      @RequestParam(name = "performance_datetime") @Nullable LocalDateTime performanceDatetime,
                                      @RequestParam(name = "price_lodge") @Nullable Long priceLodge,
                                      @RequestParam(name = "price_parterre") @Nullable Long priceParterre,
                                      @RequestParam(name = "price_amphitheater") @Nullable Long priceAmphitheater,
                                      @RequestParam(name = "price_mezzanine") @Nullable Long priceMezzanine,
                                      @RequestParam(name = "price_balcony") @Nullable Long priceBalcony,
                                      Model model) {
        Performance performance = performanceDAO.getById(performanceId);

        if (performanceDatetime == null) {
            model.addAttribute("error_msg", "Выступление должно иметь непустые дату и время");
            return "errorPage";
        }
        if (priceLodge == null || priceParterre == null || priceAmphitheater == null || priceMezzanine == null || priceBalcony == null) {
            model.addAttribute("error_msg", "Стоимоть билета должна быть непустой");
            return "errorPage";
        }

        List<TicketPrice> ticketPriceList = ticketPriceDAO.getByFilter(TicketPriceDAO.Filter.builder().performanceId(performanceId).build());
        Map<PlaceType, TicketPrice> ticketPriceMap = new HashMap<>();
        for (TicketPrice ticketPrice : ticketPriceList) {
            ticketPriceMap.put(ticketPrice.getPlaceType(), ticketPrice);
        }

        if (performance != null) {
            performance.setDatetime(performanceDatetime);
            performance.setPlay(playDAO.getById(playId));
            performanceDAO.update(performance);

            ticketPriceMap.get(PlaceType.LODGE).setPrice(priceLodge);
            ticketPriceDAO.update(ticketPriceMap.get(PlaceType.LODGE));
            ticketPriceMap.get(PlaceType.PARTERRE).setPrice(priceParterre);
            ticketPriceDAO.update(ticketPriceMap.get(PlaceType.PARTERRE));
            ticketPriceMap.get(PlaceType.AMPHITHEATER).setPrice(priceAmphitheater);
            ticketPriceDAO.update(ticketPriceMap.get(PlaceType.AMPHITHEATER));
            ticketPriceMap.get(PlaceType.MEZZANINE).setPrice(priceMezzanine);
            ticketPriceDAO.update(ticketPriceMap.get(PlaceType.MEZZANINE));
            ticketPriceMap.get(PlaceType.BALCONY).setPrice(priceBalcony);
            ticketPriceDAO.update(ticketPriceMap.get(PlaceType.BALCONY));
        } else {
            performance = new Performance(performanceDatetime, playDAO.getById(playId));
            performanceDAO.save(performance);

            ticketPriceDAO.save(new TicketPrice(performance, PlaceType.LODGE, priceLodge));
            ticketPriceDAO.save(new TicketPrice(performance, PlaceType.PARTERRE, priceParterre));
            ticketPriceDAO.save(new TicketPrice(performance, PlaceType.AMPHITHEATER, priceAmphitheater));
            ticketPriceDAO.save(new TicketPrice(performance, PlaceType.MEZZANINE, priceMezzanine));
            ticketPriceDAO.save(new TicketPrice(performance, PlaceType.BALCONY, priceBalcony));
        }
        return "redirect:/performance?performance_id=" + performance.getId();
    }
    @PostMapping("/performanceDelete")
    public String performanceDeletePage(@RequestParam(name = "performance_id") Long performanceId) {
        performanceDAO.deleteById(performanceId);
        return "redirect:/performanceList";
   }
}
