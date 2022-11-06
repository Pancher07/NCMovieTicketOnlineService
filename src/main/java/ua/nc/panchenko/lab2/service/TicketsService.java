package ua.nc.panchenko.lab2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.nc.panchenko.lab2.models.*;
import ua.nc.panchenko.lab2.repository.TicketsRepository;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class TicketsService {
    private final TicketsRepository ticketsRepository;

    @Autowired
    public TicketsService(TicketsRepository ticketsRepository) {
        this.ticketsRepository = ticketsRepository;
    }

    public Ticket findById(Integer id) {
        Optional<Ticket> foundTicket = ticketsRepository.findById(id);
        return foundTicket.orElse(null);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    public Ticket saveTicket(Ticket ticket, CinemaHall cinemaHall) {
        ticket.setCinemaHall(cinemaHall);
        return ticketsRepository.save(ticket);
    }

    public CinemaHall getCinemaHallOfTicket(Integer id) {
        return ticketsRepository.findById(id).map(Ticket::getCinemaHall).orElse(null);
    }

    @Transactional
    public void setTicketToBucket(Ticket ticketToBasket, Basket basket) {
        ticketToBasket.setAvailable(false);
        ticketToBasket.setBasket(basket);
        ticketsRepository.save(ticketToBasket);
    }

    @Transactional
    public void removeTicketFromBasket(Ticket ticketToBasket, Basket basket) {
        ticketToBasket.setAvailable(true);
        ticketToBasket.setBasket(null);
        ticketsRepository.save(ticketToBasket);
    }
}
