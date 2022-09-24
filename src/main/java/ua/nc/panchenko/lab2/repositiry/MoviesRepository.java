package ua.nc.panchenko.lab2.repositiry;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.nc.panchenko.lab2.models.Movie;

public interface MoviesRepository extends JpaRepository<Movie, Integer> {
}
