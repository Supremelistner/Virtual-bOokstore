package profile.profiler.profile.Repos;

import profile.profiler.profile.Database.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}