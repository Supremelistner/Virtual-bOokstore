package com.files.buyerservice.Repos;

import org.springframework.data.jpa.repository.JpaRepository;
import com.files.buyerservice.Database.ImageDB;

public interface ImageDBRepository extends JpaRepository<ImageDB, Integer> {
}