package com.babel.movieapp.movies.service;

import com.babel.movieapp.movies.model.Movie;
import com.babel.movieapp.movies.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Iterable<Movie> findAll() {
        return movieRepository.findAll();
    }

    public Movie create(Movie movie) {
        return movieRepository.save(movie);
    }

    public void updateMovieConfirmed(Integer movieId){
        Optional<Movie> movies = movieRepository.findById(movieId);
        movies.ifPresent(movie -> {
            movie.setConfirmed(!movie.isConfirmed());
            movieRepository.save(movie);
        });
    }
}
