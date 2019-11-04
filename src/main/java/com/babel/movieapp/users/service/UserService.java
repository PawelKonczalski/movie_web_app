package com.babel.movieapp.users.service;

import com.babel.movieapp.movies.model.Movie;
import com.babel.movieapp.movies.repository.MovieRepository;
import com.babel.movieapp.users.model.User;
import com.babel.movieapp.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MovieRepository movieRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        } else {
            return new org.springframework.security.core.userdetails.
                    User(username, user.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRoles())));
        }
    }

    public User create(String username, String password) {
        User user = new User();
        UUID id = UUID.randomUUID();
        while (userRepository.existsById(id)) {
            id = UUID.randomUUID();
        }
        user.setId(id);
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        return userRepository.save(user);
    }

    public User getCurrentlyLoginUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return userRepository.findByUsername(username);
    }

    public void addMovieToUser(Integer movieId) {
        Optional<Movie> movies = movieRepository.findById(movieId);
        User user = getCurrentlyLoginUser();
        movies.ifPresent(movie -> {
            if (!user.getMovieList().contains(movie)) {
                user.getMovieList().add(movie);
                userRepository.save(user);
            }
        });
    }

    public void removeMovieFromUser(Integer movieId) {
        Optional<Movie> movies = movieRepository.findById(movieId);
        User user = getCurrentlyLoginUser();
        movies.ifPresent(movie -> {
            if (user.getMovieList().contains(movie)) {
                user.getMovieList().remove(movie);
                userRepository.save(user);
            }
        });
    }

    public void deleteMovie(Integer movieId) {
        Optional<Movie> movies = movieRepository.findById(movieId);
        Iterable<User> users = userRepository.findAll();
        movies.ifPresent(movie -> users.forEach(user -> {
            if (user.getMovieList().contains(movie)) {
                user.getMovieList().remove(movie);
                userRepository.save(user);
            }
        }));
        movieRepository.deleteById(movieId);
    }
}
