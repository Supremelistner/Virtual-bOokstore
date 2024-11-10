package profile.profiler.profile.Repos;

import profile.profiler.profile.Database.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewsRepository extends JpaRepository<Reviews, Integer> {
}