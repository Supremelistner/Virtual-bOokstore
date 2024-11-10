package profile.profiler.profile.Repos;

import profile.profiler.profile.Database.Books;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BooksRepository extends JpaRepository<Books, Long> {
}