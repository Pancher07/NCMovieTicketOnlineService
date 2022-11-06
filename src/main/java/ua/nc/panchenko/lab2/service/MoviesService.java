package ua.nc.panchenko.lab2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.nc.panchenko.lab2.models.Movie;
import ua.nc.panchenko.lab2.models.Role;
import ua.nc.panchenko.lab2.models.Session;
import ua.nc.panchenko.lab2.repository.MoviesRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class MoviesService {
    private final MoviesRepository moviesRepository;

    @Autowired
    public MoviesService(MoviesRepository moviesRepository) {
        this.moviesRepository = moviesRepository;
    }

    public Movie findById(Integer id) {
        Optional<Movie> foundMovie = moviesRepository.findById(id);
        return foundMovie.orElse(null);
    }

    public Optional<Movie> findByTitle(String title) {
        return moviesRepository.findByTitle(title);
    }

    public List<Movie> findAll() {
        return moviesRepository.findAll();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    public Movie saveMovie(Movie movie) {
        return moviesRepository.save(movie);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    public void update(int id, Movie updatedMovie) {
        updatedMovie.setId(id);
        moviesRepository.save(updatedMovie);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    public void deleteById(Integer id) {
        moviesRepository.deleteById(id);
    }

    public List<Session> getSessionsByMovieId(int id) {
        Optional<Movie> movie = moviesRepository.findById(id);
        if (movie.isPresent()) {
            return movie.get().getSessions();
        } else {
            return Collections.emptyList();
        }
    }
}
