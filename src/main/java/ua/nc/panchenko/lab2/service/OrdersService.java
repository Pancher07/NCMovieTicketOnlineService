package ua.nc.panchenko.lab2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.nc.panchenko.lab2.models.Order;
import ua.nc.panchenko.lab2.models.Ticket;
import ua.nc.panchenko.lab2.models.User;
import ua.nc.panchenko.lab2.repository.OrdersRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class OrdersService {
    private final OrdersRepository ordersRepository;

    @Autowired
    public OrdersService(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    public Order findById(Integer id) {
        Optional<Order> foundOrder = ordersRepository.findById(id);
        return foundOrder.orElse(null);
    }

    @Transactional
    public Order saveOrder(Order order, User user) {
        List<Ticket> tickets = user.getBasket().getTickets();
        order.setUser(user);
        order.setTotalPrice(user.getBasket().getTotalPrice());
        for (Ticket ticket : tickets) {
            ticket.setBasket(null);     //очищаем корзину
            ticket.setOrder(order);
        }
        return ordersRepository.save(order);
    }

}
