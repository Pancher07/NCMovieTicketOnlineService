package ua.nc.panchenko.lab2.controllers;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.nc.panchenko.lab2.models.Movie;
import ua.nc.panchenko.lab2.service.MoviesService;
import ua.nc.panchenko.lab2.service.UsersDetailsService;

import javax.validation.Valid;

@Controller
@Log
@RequestMapping("/movies")
public class MoviesController {
    private final MoviesService moviesService;
    private final UsersDetailsService usersDetailsService;

    @Autowired
    public MoviesController(MoviesService moviesService, UsersDetailsService usersDetailsService) {
        this.moviesService = moviesService;
        this.usersDetailsService = usersDetailsService;
    }

    @GetMapping()
    public String findAll(Model model) {
        log.info("Handling find all movies request");
        model.addAttribute("movies", moviesService.findAll());
        return "movies/movie-list";
    }

    @GetMapping("/{id}")
    public String showMovie(@PathVariable("id") int id, Model model) {
        log.info("Handling show movie information request");
        model.addAttribute("movie", moviesService.findById(id));
        model.addAttribute("sessions", moviesService.getSessionsByMovieId(id));
        return "movies/movie-page";
    }

    @GetMapping("/movie-create")
    public String createMovie(@ModelAttribute("movie") Movie movie) {
        return "movies/movie-create";
    }

    @PostMapping()
    public String saveMovie(@ModelAttribute("movie") @Valid Movie movie, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "movies/movie-create";
        }
        log.info("Handling save movie: " + movie);
        moviesService.saveMovie(movie);
        return "redirect:/movies";
    }

    @GetMapping("/movie-delete/{id}")
    public String deleteMovie(@PathVariable Integer id) {
        log.info("Handling delete movie request: " + id);
        moviesService.deleteById(id);
        return "redirect:/movies";
    }

    @GetMapping("/{id}/movie-edit")
    public String edit(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("movie", moviesService.findById(id));
        return "movies/movie-edit";
    }

    @PatchMapping("/{id}")
    public String updateMovie(@ModelAttribute("movie") @Valid Movie movie, BindingResult bindingResult,
                              @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "movies/movie-edit";
        }
        log.info("Handling update movie request: " + id);
        moviesService.update(id, movie);
        return "redirect:/movies";
    }
}
