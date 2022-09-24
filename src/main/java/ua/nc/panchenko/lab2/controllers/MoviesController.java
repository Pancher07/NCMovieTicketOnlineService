package ua.nc.panchenko.lab2.controllers;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.nc.panchenko.lab2.models.Movie;
import ua.nc.panchenko.lab2.service.MoviesService;

import javax.validation.Valid;

@Controller
@Log
@RequestMapping("/movies")
public class MoviesController {
    private final MoviesService moviesService;

    @Autowired
    public MoviesController(MoviesService moviesService) {
        this.moviesService = moviesService;
    }

    @GetMapping()
    public String findAll(Model model) {
        log.info("Handling find all movies request");
        model.addAttribute("movies", moviesService.findAll());
        return "movie-list";
    }

    @GetMapping("/{id}")
    public String showMovie(@PathVariable("id") int id, Model model) {
        log.info("Handling show movie information request");
        model.addAttribute("movie", moviesService.findById(id));
        return "movie-page";
    }

    @GetMapping("/movie-create")
    public String createMovie(@ModelAttribute("movie") Movie movie) {
        return "movie-create";
    }

    @PostMapping()
    public String saveMovie(@ModelAttribute("movie") @Valid Movie movie, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "movie-create";
        }
        log.info("Handling save users: " + movie);
        moviesService.saveMovie(movie);
        return "redirect:/movies";
    }

    @GetMapping("movie-delete/{id}")
    public String deleteMovie(@PathVariable Integer id) {
        log.info("Handling delete movie request: " + id);
        moviesService.deleteById(id);
        return "redirect:/movies";
    }

    @GetMapping("/{id}/movie-edit")
    public String edit(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("movie", moviesService.findById(id));
        return "movie-edit";
    }

    @PostMapping("/{id}")
    public String updateUser(@Valid Movie movie, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "movie-edit";
        }
        log.info("Handling update movie request: " + movie);
        moviesService.saveMovie(movie);
        return "redirect:/movies";
    }
}
