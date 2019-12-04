package com.babel.movieapp.movies.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String movieImg;
    private String movieTitle;
    @ElementCollection
    private List<MovieCategory> movieCategory;
    @Column(length = 1024)
    private String description;
    private boolean confirmed;
    private String author;
}
