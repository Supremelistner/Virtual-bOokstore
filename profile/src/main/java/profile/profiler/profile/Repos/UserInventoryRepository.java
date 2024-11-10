package profile.profiler.profile.Repos;

import profile.profiler.profile.Database.UserInventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInventoryRepository extends JpaRepository<UserInventory, Integer> {
}