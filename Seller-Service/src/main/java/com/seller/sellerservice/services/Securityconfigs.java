package com.seller.sellerservice.services;

import com.seller.sellerservice.Repos.LocalUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class Securityconfigs {
    @Autowired
    JwtAuthPoint jwtAuthPoint;
    @Autowired
    LocalUsersRepository localUserService;

    @Bean
    protected SecurityFilterChain webFilterChain(HttpSecurity http)throws Exception{
        return http.authorizeHttpRequests(request-> request.anyRequest().permitAll())
                .addFilterBefore(jwtAuthPoint, UsernamePasswordAuthenticationFilter.class)
                .csrf().disable()
                .build();

    }
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
