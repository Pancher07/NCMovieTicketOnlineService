package ua.nc.panchenko.lab2.controllers;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.nc.panchenko.lab2.models.Movie;
import ua.nc.panchenko.lab2.models.Session;
import ua.nc.panchenko.lab2.service.MoviesService;
import ua.nc.panchenko.lab2.service.SessionsService;

import javax.validation.Valid;

@Controller
@Log
@RequestMapping("/movies/{id}/sessions")
public class SessionsController {
    private final SessionsService sessionsService;
    private final MoviesService moviesService;

    @Autowired
    public SessionsController(SessionsService sessionsService, MoviesService moviesService) {
        this.sessionsService = sessionsService;
        this.moviesService = moviesService;
    }

    @GetMapping()
    public String findAllSessionsByMovie(@PathVariable("id") int id, Model model) {
        log.info("Handling find all sessions request");
        model.addAttribute("sessions", moviesService.getSessionsByMovieId(id));
        model.addAttribute("movie", moviesService.findById(id));
        return "sessions/session-list-page";
    }

    @GetMapping("/session-create")
    public String createSession(@ModelAttribute("session") Session session, @PathVariable("id") int id, Model model) {
        model.addAttribute("movie", moviesService.findById(id));
        return "sessions/session-create";
    }

    @PostMapping()
    public String saveSession(@ModelAttribute("session") @Valid Session session, @PathVariable("id") int id,
                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "sessions/session-create";
        }
        log.info("Handling save session: " + session);
        sessionsService.saveSession(session, moviesService.findById(id));
        return "redirect:/movies/{id}/sessions";
    }

    @GetMapping("/session-delete/{session_id}")
    public String deleteSession(@PathVariable("session_id") Integer id) {
        log.info("Handling delete session request: " + id);
        sessionsService.deleteById(id);
        return "redirect:/movies/{id}/sessions";
    }

    @GetMapping("/{session_id}/session-edit")
    public String edit(@PathVariable("id") Integer movieId, @PathVariable("session_id") Integer sessionId,
                       Model model) {
        model.addAttribute("movie", moviesService.findById(movieId));
        model.addAttribute("sess", sessionsService.findById(sessionId));
        return "sessions/session-edit";
    }

    @PatchMapping("/{session_id}")
    public String updateMovie(@PathVariable("id") Integer movieId, @PathVariable("session_id") Integer sessionId,
                              @ModelAttribute("sess") @Valid Session session, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "sessions/session-edit";
        }
        sessionsService.update(sessionId, session, moviesService.findById(movieId));
        return "redirect:/movies/{id}/sessions";
    }
}
