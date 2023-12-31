package com.fooddelivery.backend.Repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fooddelivery.backend.Models.Owner;
import  com.fooddelivery.backend.Models.Users;

@Repository
public interface OwnerRepos extends JpaRepository<Owner, Long>{
    Optional<Owner> findByUser(Users user);
}
