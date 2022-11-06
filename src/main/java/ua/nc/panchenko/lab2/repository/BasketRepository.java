package ua.nc.panchenko.lab2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.nc.panchenko.lab2.models.Basket;

public interface BasketRepository extends JpaRepository<Basket, Integer> {
}
