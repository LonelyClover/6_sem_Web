package ru.msu.cs.TheaterWeb.DAO.impl;

import org.springframework.stereotype.Repository;
import ru.msu.cs.TheaterWeb.DAO.TicketPriceDAO;
import ru.msu.cs.TheaterWeb.entities.TicketPrice;

@Repository
public class TicketPriceDAOImpl extends CommonDAOImpl<TicketPrice> implements TicketPriceDAO {
    public TicketPriceDAOImpl() {
        super(TicketPrice.class);
    }
}