package profile.profiler.profile.Repos;

import profile.profiler.profile.Database.Credentials;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CredentialsRepository extends JpaRepository<Credentials, Integer> {
}