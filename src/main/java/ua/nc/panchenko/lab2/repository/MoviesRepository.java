package ua.nc.panchenko.lab2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.nc.panchenko.lab2.models.Movie;

import java.util.List;
import java.util.Optional;

public interface MoviesRepository extends JpaRepository<Movie, Integer> {
    @Override
    @Query(value = "select * from lab2_pdv_movie order by id", nativeQuery = true)
    List<Movie> findAll();

    Optional<Movie> findByTitle(String title);
}
