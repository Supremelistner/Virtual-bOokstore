package com.seller.sellerservice.Repos;

import com.seller.sellerservice.Database.Books;
import com.seller.sellerservice.Database.LocalUsers;
import com.seller.sellerservice.Database.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.seller.sellerservice.Database.Sellerinventory;

import java.util.List;
import java.util.Optional;

public interface SellerinventoryRepository extends JpaRepository<Sellerinventory, Long> {

Optional<Sellerinventory> findByLocalUsers(LocalUsers users);



}