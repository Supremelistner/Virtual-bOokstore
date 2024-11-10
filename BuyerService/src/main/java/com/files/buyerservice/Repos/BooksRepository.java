package com.files.buyerservice.Repos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.files.buyerservice.Database.Books;

import java.util.List;

public interface BooksRepository extends JpaRepository<Books, Long> {
    Page<Books> findBySellerinventory_Id(Integer id, Pageable page);

    Page<Books> findByUserInventory_Id(Integer id, Pageable pageable);
}