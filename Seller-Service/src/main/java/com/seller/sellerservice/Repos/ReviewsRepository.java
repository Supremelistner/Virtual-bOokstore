package com.seller.sellerservice.Repos;

import com.seller.sellerservice.Database.Books;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.seller.sellerservice.Database.Reviews;

import java.util.List;

public interface ReviewsRepository extends JpaRepository<Reviews, Integer> {
    List<Reviews> findByBooks_Id(Long id, Pageable pageable);

    Page<Reviews> findByBooks(Books books, Pageable pageable);
}