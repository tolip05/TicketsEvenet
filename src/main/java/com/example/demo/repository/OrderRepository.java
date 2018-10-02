package com.example.demo.repository;

import com.example.demo.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,String> {

    @Query(value = "SELECT * FROM orders WHERE user_id = :userId"
            , nativeQuery = true)
    List<Order> findAllByUserId(@Param("userId") String userId);
}
