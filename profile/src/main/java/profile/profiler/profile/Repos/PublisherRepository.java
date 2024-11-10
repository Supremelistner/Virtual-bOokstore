package profile.profiler.profile.Repos;

import profile.profiler.profile.Database.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {
}