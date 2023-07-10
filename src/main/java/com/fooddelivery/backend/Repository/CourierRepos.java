package com.fooddelivery.backend.Repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fooddelivery.backend.Models.Courier;
import  com.fooddelivery.backend.Models.Users;
import com.fooddelivery.backend.Models.Enums.CourierStatus;

@Repository
public interface CourierRepos extends JpaRepository<Courier, Long>  {
    Optional<Courier> findByUser(Users user);
    List<Courier> findByAvailable(boolean available);
}
