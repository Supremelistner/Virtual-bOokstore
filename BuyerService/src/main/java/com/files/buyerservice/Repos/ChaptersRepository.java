package com.files.buyerservice.Repos;

import org.springframework.data.jpa.repository.JpaRepository;
import com.files.buyerservice.Database.Chapters;

public interface ChaptersRepository extends JpaRepository<Chapters, Long> {
}