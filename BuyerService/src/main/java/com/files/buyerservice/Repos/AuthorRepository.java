package com.files.buyerservice.Repos;

import org.springframework.data.jpa.repository.JpaRepository;
import com.files.buyerservice.Database.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}