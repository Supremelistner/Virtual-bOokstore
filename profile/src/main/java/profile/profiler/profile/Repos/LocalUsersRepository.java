package profile.profiler.profile.Repos;

import org.springframework.security.core.userdetails.UserDetailsService;
import profile.profiler.profile.Database.LocalUsers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalUsersRepository extends JpaRepository<LocalUsers, Long> {
    LocalUsers findByUsernameIgnoreCase(String username);

    LocalUsers findByEmail(String email);

    LocalUsers getByUsername(String username);


}