package com.fooddelivery.backend.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fooddelivery.backend.Models.Image;



@Repository
public interface ImageRepos extends JpaRepository<Image, Long> {
    Optional<Image> findByFileName(String fileName);
}
