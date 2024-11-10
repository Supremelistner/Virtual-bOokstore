package com.files.buyerservice.Repos;

import org.springframework.data.jpa.repository.JpaRepository;
import com.files.buyerservice.Database.Sellerinventory;

public interface SellerinventoryRepository extends JpaRepository<Sellerinventory, Long> {
}