package com.files.buyerservice.Repos;

import com.files.buyerservice.Database.LocalUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import com.files.buyerservice.Database.UserInventory;

import java.util.Optional;

public interface UserInventoryRepository extends JpaRepository<UserInventory, Integer> {


    Optional<UserInventory> findByLocalUsers(LocalUsers localUsers);
}