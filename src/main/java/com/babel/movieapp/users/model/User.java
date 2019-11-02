package com.babel.movieapp.users.model;

import com.babel.movieapp.movies.model.Movie;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String password;
    private String roles = "USER";
    @ManyToMany
    private List<Movie> movieList = new ArrayList<>();
}
