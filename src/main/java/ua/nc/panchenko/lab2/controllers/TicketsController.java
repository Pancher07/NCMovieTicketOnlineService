package ua.nc.panchenko.lab2.controllers;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.nc.panchenko.lab2.models.Ticket;
import ua.nc.panchenko.lab2.models.User;
import ua.nc.panchenko.lab2.security.UsersDetails;
import ua.nc.panchenko.lab2.service.*;

import java.security.Principal;

@Controller
@Log
@RequestMapping("/movies/{movie_id}/sessions/{session_id}/{ticket_id}")
public class TicketsController {
    private final TicketsService ticketsService;
    private final CinemaHallsService cinemaHallsService;
    private final SessionsService sessionsService;
    private final MoviesService moviesService;
    private final UsersDetailsService usersDetailsService;

    @Autowired
    public TicketsController(TicketsService ticketsService, CinemaHallsService cinemaHallsService,
                             SessionsService sessionsService, MoviesService moviesService, UsersDetailsService usersDetailsService) {
        this.ticketsService = ticketsService;
        this.cinemaHallsService = cinemaHallsService;
        this.sessionsService = sessionsService;
        this.moviesService = moviesService;
        this.usersDetailsService = usersDetailsService;
    }

    @GetMapping()
    public String showTicket(@PathVariable("movie_id") int movieId, @PathVariable("session_id") int sessionId,
                             @PathVariable("ticket_id") int id, Model model) {
        log.info("Handling show ticket information request");
        model.addAttribute("ticket", ticketsService.findById(id));
        model.addAttribute("cinemaHall", ticketsService.getCinemaHallOfTicket(id));
        model.addAttribute("movie", moviesService.findById(movieId));
        model.addAttribute("sess", sessionsService.findById(sessionId));

        return "tickets/ticket-page";
    }

    @GetMapping("/add-to-basket")
    public String addTicketToBasket(@PathVariable("ticket_id") int ticketId, Principal principal) {
        User user = ((UsersDetails) usersDetailsService.loadUserByUsername(principal.getName())).getUser();
        log.info("Handling add ticket " + ticketId + " to basket " + user.getBasket().getId());
        Ticket ticket = ticketsService.findById(ticketId);
        ticketsService.setTicketToBucket(ticket, user.getBasket());
        return "redirect:/movies/{movie_id}/sessions/{session_id}";
    }

    @GetMapping("/remove-from-basket")
    public String removeTicketFromBasket(@PathVariable("ticket_id") int ticketId, Principal principal) {
        User user = ((UsersDetails) usersDetailsService.loadUserByUsername(principal.getName())).getUser();
        log.info("Handling remove ticket " + ticketId + " from basket " + user.getBasket().getId());
        Ticket ticket = ticketsService.findById(ticketId);
        ticketsService.removeTicketFromBasket(ticket, user.getBasket());
        return "redirect:/movies/{movie_id}/sessions/{session_id}";
    }
}
