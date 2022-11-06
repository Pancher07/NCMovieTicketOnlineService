package ua.nc.panchenko.lab2.controllers;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.nc.panchenko.lab2.models.Basket;
import ua.nc.panchenko.lab2.models.Ticket;
import ua.nc.panchenko.lab2.models.User;
import ua.nc.panchenko.lab2.security.UsersDetails;
import ua.nc.panchenko.lab2.service.BasketsService;
import ua.nc.panchenko.lab2.service.TicketsService;
import ua.nc.panchenko.lab2.service.UsersDetailsService;

import java.security.Principal;
import java.util.List;

@Controller
@Log
@RequestMapping("/basket")
public class BasketsController {
    private final BasketsService basketsService;
    private final UsersDetailsService usersDetailsService;
    private final TicketsService ticketsService;

    @Autowired
    public BasketsController(BasketsService basketsService, UsersDetailsService usersDetailsService,
                             TicketsService ticketsService) {
        this.basketsService = basketsService;
        this.usersDetailsService = usersDetailsService;
        this.ticketsService = ticketsService;
    }

    @GetMapping()
    public String showBucket(Model model, Principal principal) {
        log.info("Handling show bucket information request");
        User user = ((UsersDetails) usersDetailsService.loadUserByUsername(principal.getName())).getUser();
        Basket basket = user.getBasket();
        List<Ticket> tickets = basketsService.getTicketsByBucketId(basket.getId());
        model.addAttribute("user", user);
        model.addAttribute("tickets", tickets);
        model.addAttribute("basket", basket);
        return "basket/basket-page";
    }

    @GetMapping("/ticket-remove/{ticket_id}")
    public String removeTicketFromBucket(@PathVariable("ticket_id") int ticketId, Principal principal) {
        User user = ((UsersDetails) usersDetailsService.loadUserByUsername(principal.getName())).getUser();
        log.info("Handling remove ticket " + ticketId + " from basket " + user.getBasket().getId());
        Ticket ticket = ticketsService.findById(ticketId);
        ticketsService.removeTicketFromBasket(ticket, user.getBasket());
        return "redirect:/basket";
    }
}
