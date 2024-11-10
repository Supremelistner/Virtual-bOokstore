package com.files.buyerservice.Repos;

import org.springframework.data.jpa.repository.JpaRepository;
import com.files.buyerservice.Database.Reviews;

public interface ReviewsRepository extends JpaRepository<Reviews, Integer> {
    Reviews findFirstByUserInventory_IdAndBooks_Id(Integer id, Long id1);
}