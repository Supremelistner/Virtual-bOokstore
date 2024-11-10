package com.files.buyerservice.Repos;

import org.springframework.data.jpa.repository.JpaRepository;
import com.files.buyerservice.Database.Credentials;

public interface CredentialsRepository extends JpaRepository<Credentials, Integer> {
}