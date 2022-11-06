package ua.nc.panchenko.lab2.controllers;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.nc.panchenko.lab2.models.CinemaHall;
import ua.nc.panchenko.lab2.models.Ticket;
import ua.nc.panchenko.lab2.models.User;
import ua.nc.panchenko.lab2.security.UsersDetails;
import ua.nc.panchenko.lab2.service.*;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@Log
@RequestMapping("/movies/{movie_id}/sessions/{session_id}")
public class CinemaHallsController {
    private final SessionsService sessionsService;
    private final MoviesService moviesService;
    private final CinemaHallsService cinemaHallsService;
    private final TicketsService ticketsService;
    private final UsersDetailsService usersDetailsService;

    @Autowired
    public CinemaHallsController(SessionsService sessionsService, MoviesService moviesService,
                                 CinemaHallsService cinemaHallsService, TicketsService ticketsService, UsersDetailsService usersDetailsService) {
        this.sessionsService = sessionsService;
        this.moviesService = moviesService;
        this.cinemaHallsService = cinemaHallsService;
        this.ticketsService = ticketsService;
        this.usersDetailsService = usersDetailsService;
    }

    @GetMapping()
    public String showTicketsInCinemaHall(@PathVariable("movie_id") int movieId,
                                          @PathVariable("session_id") int sessionId, Model model, Principal principal) {
        log.info("Handling show tickets of session information request");
        User user = ((UsersDetails) usersDetailsService.loadUserByUsername(principal.getName())).getUser();
        model.addAttribute("basket", user.getBasket());
        model.addAttribute("movie", moviesService.findById(movieId));
        model.addAttribute("sess", sessionsService.findById(sessionId));
        model.addAttribute("cinemaHall", sessionsService.getCinemaHallBySessionId(sessionId));

        //создание макета в матрицы массива для отображения мест в зале на html-странице
        if (sessionsService.getCinemaHallBySessionId(sessionId) != null) {
            int[][] matrix = new int[sessionsService.getCinemaHallBySessionId(sessionId).getNumberOfRows()]
                    [sessionsService.getCinemaHallBySessionId(sessionId).getNumberSeatsInRow()];
            int numberOfTicket = 0;
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[i].length; j++) {
                    matrix[i][j] = numberOfTicket++;
                }
            }
            model.addAttribute("matrix", matrix);
            model.addAttribute("tickets", sessionsService.
                    getCinemaHallBySessionId(sessionId).getSortedHallLayout());
        }

        return "sessions/session-page";
    }

    @GetMapping("/cinemaHall-create")
    public String createTicketsInCinemaHall(@ModelAttribute("cinemaHall") CinemaHall cinemaHall,
                                            @PathVariable("movie_id") int movieId,
                                            @PathVariable("session_id") int sessionId, Model model) {
        if (sessionsService.findById(sessionId).getCinemaHall() == null) {
            model.addAttribute("movie", moviesService.findById(movieId));
            model.addAttribute("sess", sessionsService.findById(sessionId));
            return "cinemaHall/cinemaHall-create";
        } else {
            return "redirect:/movies/{movie_id}/sessions/{session_id}";
        }
    }

    @PostMapping()
    public String saveTicketsInCinemaHall(@ModelAttribute("cinemaHall") @Valid CinemaHall cinemaHall,
                                          @PathVariable("session_id") int sessionId, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "cinemaHall/cinemaHall-create";
        }
        log.info("Handling save cinema hall: " + cinemaHall);
        cinemaHallsService.saveCinemaHall(cinemaHall, sessionsService.findById(sessionId));
        cinemaHall.creatTicketsInCinemaHall(cinemaHall.getNumberOfRows(), cinemaHall.getNumberSeatsInRow(),
                cinemaHall.getPriceForSeat());
        for (Ticket ticket : cinemaHall.getHallLayout()) {
            ticketsService.saveTicket(ticket, cinemaHall);
        }
        return "redirect:/movies/{movie_id}/sessions/{session_id}";
    }

}
