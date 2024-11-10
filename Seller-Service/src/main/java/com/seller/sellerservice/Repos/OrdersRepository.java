package com.seller.sellerservice.Repos;

import com.seller.sellerservice.Database.Orders;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders, Integer> {


    List<Orders> findBySellerinventory_Id(long id, Pageable pageable);
}