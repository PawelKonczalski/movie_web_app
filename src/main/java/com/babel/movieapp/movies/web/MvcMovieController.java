package com.babel.movieapp.movies.web;

import com.babel.movieapp.movies.model.Movie;
import com.babel.movieapp.movies.model.MovieCategory;
import com.babel.movieapp.movies.service.FileService;
import com.babel.movieapp.movies.service.MovieService;
import com.babel.movieapp.users.service.UserService;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class MvcMovieController {

    private final MovieService movieService;
    private final UserService userService;
    private final FileService fileService;

    public MvcMovieController(MovieService movieService, UserService userService, FileService fileService) {
        this.movieService = movieService;
        this.userService = userService;
        this.fileService = fileService;
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
    public String addMovie(Movie movie, @RequestParam(value = "file") MultipartFile file) {
        if (!file.isEmpty()) {
            fileService.saveFile(file);
            movie.setMovieImg(file.getOriginalFilename());
        }
        movie.setAuthor(userService.getCurrentlyLoginUser().getUsername());
        movieService.create(movie);
        return "redirect:/movies";
    }

    @GetMapping
    @RequestMapping("/files")
    public ResponseEntity<Resource> downloadFile(@RequestParam("file") String file) {
        return fileService.responseEntity(file);
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
