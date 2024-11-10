package com.files.buyerservice.Repos;

import org.springframework.data.jpa.repository.JpaRepository;
import com.files.buyerservice.Database.Publisher;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {
}