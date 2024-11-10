package com.files.buyerservice.Repos;

import com.files.buyerservice.Database.UserInventory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.files.buyerservice.Database.Orders;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders, Integer> {



    Orders findByChapters_IdAndUserInventory_Id(Long id, Integer id1);

    List<Orders> findByUserInventory(UserInventory userInventory, Pageable pageable);

    @Query("select o from Orders o where o.invoice = ?1")
    Orders findByInvoice(String invoice);

    @Query("select o from Orders o where o.chapters.filePath = ?1")
    Orders findByChapters_FilePath(String filePath);
}