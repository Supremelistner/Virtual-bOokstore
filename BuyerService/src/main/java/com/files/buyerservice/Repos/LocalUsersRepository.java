package com.files.buyerservice.Repos;

import org.springframework.data.jpa.repository.JpaRepository;
import com.files.buyerservice.Database.LocalUsers;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface LocalUsersRepository extends JpaRepository<LocalUsers, Long>, UserDetailsService {
    LocalUsers findByUsernameIgnoreCase(String username);

    LocalUsers findByEmail(String email);

    @Override
    default UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(findByUsernameIgnoreCase(username)==null)
            throw new RuntimeException();
        LocalUsers user=findByUsernameIgnoreCase(username);
        return user;}
}