package com.example.sample.repository;

import com.example.sample.models.UserStockBalance;
import com.example.sample.models.UserStockBalanceId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStockBalanceRepository extends JpaRepository <UserStockBalance, UserStockBalanceId>{
}
