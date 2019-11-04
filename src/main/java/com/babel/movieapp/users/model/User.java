package com.babel.movieapp.users.model;

import com.babel.movieapp.movies.model.Movie;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    private UUID id;
    private String username;
    private String password;
    private String roles = "USER";
    @ManyToMany
    private List<Movie> movieList = new ArrayList<>();

    public User(UUID id, String username, String password, String roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }
}
