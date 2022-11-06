package ua.nc.panchenko.lab2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.nc.panchenko.lab2.models.CinemaHall;
import ua.nc.panchenko.lab2.models.Session;
import ua.nc.panchenko.lab2.repository.CinemaHallRepository;

@Service
@Transactional(readOnly = true)
public class CinemaHallsService {
    private final CinemaHallRepository cinemaHallRepository;

    @Autowired
    public CinemaHallsService(CinemaHallRepository cinemaHallRepository) {
        this.cinemaHallRepository = cinemaHallRepository;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    public CinemaHall saveCinemaHall(CinemaHall cinemaHall, Session session) {
        cinemaHall.setSessionOfCinemaHall(session);
        return cinemaHallRepository.save(cinemaHall);
    }
}
