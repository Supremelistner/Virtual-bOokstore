package com.seller.sellerservice.Repos;

import org.springframework.data.jpa.repository.JpaRepository;
import com.seller.sellerservice.Database.Publisher;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;

import java.util.List;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {



    List<Publisher> findByPubContains(@Nullable String pub);
}