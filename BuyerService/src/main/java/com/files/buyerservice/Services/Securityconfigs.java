package com.files.buyerservice.Services;

import com.files.buyerservice.Repos.LocalUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
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
     http.authorizeHttpRequests(request-> request.anyRequest().permitAll());
               http.addFilterBefore(jwtAuthPoint, UsernamePasswordAuthenticationFilter.class);
                http.csrf(csrf->csrf.disable());
                http.cors(cors->cors.disable());


             return   http.build();

    }
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
