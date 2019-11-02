package com.babel.movieapp.movies.repository;

import com.babel.movieapp.movies.model.Movie;
import org.springframework.data.repository.CrudRepository;

public interface MovieRepository extends CrudRepository<Movie, Integer> {
}
