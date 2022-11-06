package ua.nc.panchenko.lab2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.nc.panchenko.lab2.models.*;
import ua.nc.panchenko.lab2.repository.BasketRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BasketsService {
    private final BasketRepository basketRepository;

    @Autowired
    public BasketsService(BasketRepository basketRepository) {
        this.basketRepository = basketRepository;
    }

    @Transactional
    public List<Ticket> getTicketsByBucketId(int id) {
        Optional<Basket> bucket = basketRepository.findById(id);
        if (bucket.isPresent()) {
            bucket.get().setTotalPrice(bucket.get()
                    .calculateTotalPrice(bucket.get().getTickets())); //считаем и сохраняем в БД total_price
            basketRepository.save(bucket.get());
            return bucket.get().getTickets();
        } else {
            return Collections.emptyList();
        }
    }
}
