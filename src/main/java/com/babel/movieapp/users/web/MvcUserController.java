package com.babel.movieapp.users.web;

import com.babel.movieapp.users.dto.RegisterUserDto;
import com.babel.movieapp.users.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MvcUserController {

    private final UserService userService;

    public MvcUserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/addUser")
    public String addUser(RegisterUserDto dto) {
        if (!dto.passwordsMatch()) {
            return "redirect:/";
        }
        userService.create(dto.getUsername(), dto.getPassword());
        return "redirect:/";
    }

    @PostMapping("/addMovieToUser/{id}")
    public String addMovieToUser(@PathVariable("id") Integer movieId) {
        userService.addMovieToUser(movieId);
        return "redirect:/movies";
    }

    @PostMapping("/removeMovieFromUser/{id}")
    public String removeMovieFromUser(@PathVariable("id") Integer movieId) {
        userService.removeMovieFromUser(movieId);
        return "redirect:/watched";
    }


}
