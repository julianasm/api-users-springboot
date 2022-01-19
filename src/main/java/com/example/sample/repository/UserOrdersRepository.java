package com.example.sample.repository;

import com.example.sample.models.UserOrders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserOrdersRepository extends JpaRepository<UserOrders, Long> {

}