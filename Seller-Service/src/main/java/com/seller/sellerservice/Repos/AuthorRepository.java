package com.seller.sellerservice.Repos;

import com.seller.sellerservice.Database.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    List<Author> findByNameContainsAllIgnoreCase(@Nullable String name);
}