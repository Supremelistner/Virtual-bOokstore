package com.seller.sellerservice.Repos;

import org.springframework.data.jpa.repository.JpaRepository;
import com.seller.sellerservice.Database.UserInventory;

public interface UserInventoryRepository extends JpaRepository<UserInventory, Integer> {
}