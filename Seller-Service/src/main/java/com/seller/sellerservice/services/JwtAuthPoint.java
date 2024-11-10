package com.seller.sellerservice.services;

import com.seller.sellerservice.Database.LocalUsers;
import com.seller.sellerservice.OpenFeign.Jwtservicing;
import com.seller.sellerservice.Repos.LocalUsersRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Configuration
public class JwtAuthPoint extends OncePerRequestFilter {

    public JwtAuthPoint(Jwtservicing jwtservicing, LocalUsersRepository users) {
        this.jwtservicing = jwtservicing;
        this.users = users;
    }

    Jwtservicing jwtservicing;
LocalUsersRepository users;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

      String jwt= request.getHeader("Authorization");
      if(jwt.startsWith("Bearer")){
          String auth= jwt.substring(7);
          String username=jwtservicing.verification(auth);
          UserDetails user=users.loadUserByUsername(username);
          UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(user,user.getAuthorities());
          authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          SecurityContextHolder.getContext().setAuthentication(authenticationToken);



        }
        chain.doFilter(request, response);
    }

}