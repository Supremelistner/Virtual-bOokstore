package com.seller.sellerservice.Repos;

import com.seller.sellerservice.Database.Books;

import com.seller.sellerservice.Database.Publisher;
import com.seller.sellerservice.Database.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;

import java.util.List;


public interface BooksRepository extends JpaRepository<Books, Long> {



    Page<Books> findBySellerinventory_Id(long id, Pageable pageable);

    Page<Books> findByAuthor(Author id, Pageable pageable);
    Page<Books> findByPublisher(Publisher id, Pageable pageable);

    Page<Books> findByBooknameContains(@Nullable String bookname,Pageable pageable);
}

