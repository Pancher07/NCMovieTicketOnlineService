package ua.nc.panchenko.lab2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.nc.panchenko.lab2.models.Movie;
import ua.nc.panchenko.lab2.repositiry.MoviesRepository;

import java.util.List;

@Service
public class MoviesService {
    private final MoviesRepository moviesRepository;

    @Autowired
    public MoviesService(MoviesRepository moviesRepository) {
        this.moviesRepository = moviesRepository;
    }

    public Movie findById(Integer id){
        return moviesRepository.getReferenceById(id);
    }

    public List<Movie> findAll(){
        return moviesRepository.findAll();
    }

    public Movie saveMovie(Movie movie){
        return moviesRepository.save(movie);
    }

    public void deleteById(Integer id){
        moviesRepository.deleteById(id);
    }
}
