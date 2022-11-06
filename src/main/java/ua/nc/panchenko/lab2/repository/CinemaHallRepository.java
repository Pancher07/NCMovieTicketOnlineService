package ua.nc.panchenko.lab2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.nc.panchenko.lab2.models.CinemaHall;

public interface CinemaHallRepository extends JpaRepository<CinemaHall, Integer> {
}
