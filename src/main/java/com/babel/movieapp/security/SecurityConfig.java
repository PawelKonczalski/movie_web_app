package com.babel.movieapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/movies").hasAnyRole("USER", "ADMIN")
                .antMatchers("/watched").hasAnyRole("USER", "ADMIN")
                .antMatchers("/createMovie").hasAnyRole("USER", "ADMIN")
                .antMatchers("/adminPanel").hasRole("ADMIN")
                .and().formLogin().loginPage("/")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/movies")
                .and().logout().logoutSuccessUrl("/")
                .and().csrf().disable()
                .exceptionHandling()
                .accessDeniedHandler(
                        (httpServletRequest, httpServletResponse, e) -> httpServletResponse.sendRedirect("/movies"));

        http.headers().frameOptions().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(NoOpPasswordEncoder.getInstance());
    }
}

