package ua.nc.panchenko.lab2.controllers;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.nc.panchenko.lab2.models.Basket;
import ua.nc.panchenko.lab2.models.Order;
import ua.nc.panchenko.lab2.models.User;
import ua.nc.panchenko.lab2.security.UsersDetails;
import ua.nc.panchenko.lab2.service.BasketsService;
import ua.nc.panchenko.lab2.service.OrdersService;
import ua.nc.panchenko.lab2.service.UsersDetailsService;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@Log
@RequestMapping("/order")
public class OrdersController {
    private final OrdersService ordersService;
    private final UsersDetailsService usersDetailsService;
    private final BasketsService basketsService;

    @Autowired
    public OrdersController(OrdersService ordersService, UsersDetailsService usersDetailsService,
                            BasketsService basketsService) {
        this.ordersService = ordersService;
        this.usersDetailsService = usersDetailsService;
        this.basketsService = basketsService;
    }

    @GetMapping()
    public String createOrder(@ModelAttribute("order") Order order, Model model, Principal principal) {
        log.info("Handling show order information request");
        User user = ((UsersDetails) usersDetailsService.loadUserByUsername(principal.getName())).getUser();
        Basket basket = user.getBasket();
        model.addAttribute("user", user);
        model.addAttribute("basket", basket);
        model.addAttribute("tickets", basket.getTickets());
        return "orders/order-page";
    }

    @PostMapping("/successful")
    public String saveOrder(@ModelAttribute("order") @Valid Order order, BindingResult bindingResult,
                            Principal principal) {
        if (bindingResult.hasErrors()) {
            return "orders/order-page";
        }
        log.info("Handling save order: " + order);
        User user = ((UsersDetails) usersDetailsService.loadUserByUsername(principal.getName())).getUser();
        ordersService.saveOrder(order, user);
        return "orders/successful-page";
    }
}
