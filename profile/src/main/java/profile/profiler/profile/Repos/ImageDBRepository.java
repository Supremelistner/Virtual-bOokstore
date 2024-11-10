package profile.profiler.profile.Repos;

import profile.profiler.profile.Database.ImageDB;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageDBRepository extends JpaRepository<ImageDB, Integer> {
}