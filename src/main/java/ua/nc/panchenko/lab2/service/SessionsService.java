package ua.nc.panchenko.lab2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.nc.panchenko.lab2.models.CinemaHall;
import ua.nc.panchenko.lab2.models.Movie;
import ua.nc.panchenko.lab2.models.Session;
import ua.nc.panchenko.lab2.models.Ticket;
import ua.nc.panchenko.lab2.repository.SessionsRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SessionsService {
    private final SessionsRepository sessionsRepository;

    @Autowired
    public SessionsService(SessionsRepository sessionsRepository) {
        this.sessionsRepository = sessionsRepository;
    }

    public Session findById(Integer id) {
        Optional<Session> foundSession = sessionsRepository.findById(id);
        return foundSession.orElse(null);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    public Session saveSession(Session session, Movie movie) {
        session.setMovieOfSessions(movie);
        return sessionsRepository.save(session);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    public void update(Integer id, Session updatedSession, Movie movie) {
        updatedSession.setId(id);
        updatedSession.setMovieOfSessions(movie);
        sessionsRepository.save(updatedSession);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    public void deleteById(Integer id) {
        sessionsRepository.deleteById(id);
    }

    public CinemaHall getCinemaHallBySessionId(int id) {
        Optional<Session> session = sessionsRepository.findById(id);
        if (session.isPresent()) {
            return session.get().getCinemaHall();
        } else {
            return (CinemaHall) Collections.emptyList();
        }
    }
}
