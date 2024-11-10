package com.seller.sellerservice.Repos;

import com.seller.sellerservice.Database.Credentials;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CredentialsRepository extends JpaRepository<Credentials, Integer> {
}