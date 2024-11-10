package profile.profiler.profile.Repos;

import profile.profiler.profile.Database.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders, Integer> {
}