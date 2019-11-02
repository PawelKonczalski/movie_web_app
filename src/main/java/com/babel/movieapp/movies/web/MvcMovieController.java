package com.babel.movieapp.movies.web;

import com.babel.movieapp.movies.model.Movie;
import com.babel.movieapp.movies.model.MovieCategory;
import com.babel.movieapp.movies.service.MovieService;
import com.babel.movieapp.users.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MvcMovieController {

    private final MovieService movieService;
    private final UserService userService;

    public MvcMovieController(MovieService movieService, UserService userService) {
        this.movieService = movieService;
        this.userService = userService;
    }

    @GetMapping("/movies")
    public String getMovie(Model model) {
        model.addAttribute("movies", movieService.findAll());
        model.addAttribute("user", userService.getCurrentlyLoginUser());
        return "movies";
    }

    @GetMapping("/watched")
    public String getViewedMovie(Model model) {
        model.addAttribute("movies", movieService.findAll());
        model.addAttribute("user", userService.getCurrentlyLoginUser());
        return "movies";
    }

    @GetMapping("/createMovie")
    public String getAddMovie(Model model) {
        model.addAttribute("movieCategory", MovieCategory.values());
        return "createmovie";
    }

    @PostMapping("/movie/createMovie")
    public String addMovie(Movie movie) {
        movieService.create(movie);
        return "redirect:/movies";
    }

    @GetMapping("/adminPanel")
    public String getAdminPanel(Model model) {
        model.addAttribute("movies", movieService.findAll());
        return "adminpanel";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer movieId) {
        userService.deleteMovie(movieId);
        return "redirect:/adminPanel";
    }

    @PostMapping("/updateConfirmed/{id}")
    public String updateConfirmed(@PathVariable("id") Integer movieId) {
        movieService.updateMovieConfirmed(movieId);
        return "redirect:/adminPanel";
    }
}
