package com.seller.sellerservice.Repos;

import com.seller.sellerservice.Database.ImageDB;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ImageDBRepository extends JpaRepository<ImageDB, Integer> {
}